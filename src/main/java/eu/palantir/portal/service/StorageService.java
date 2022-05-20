package eu.palantir.portal.service;

import eu.palantir.portal.exceptions.MinioException;
import io.minio.*;
import io.minio.messages.Item;
import io.smallrye.common.annotation.Blocking;

import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.logging.Logger;

@ApplicationScoped
public class StorageService {

    private static final Logger logger = Logger.getLogger(StorageService.class.getName());

    @Inject
    MinioClient minioClient;

    @Blocking
    public synchronized void createBucket(String bucketName) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                logger.info("Bucket " + bucketName + " already exists.");
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            throw new MinioException("Error during createBucket");
        }
    }

    @Blocking
    public synchronized void deleteBucket(String bucketName) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                Iterable<Result<Item>> objects = minioClient
                        .listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
                // remove non empty bucket
                for (Result<Item> object : objects) {
                    minioClient.removeObject(
                            RemoveObjectArgs.builder().bucket(bucketName).object(object.get().objectName()).build());
                }
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            throw new MinioException("Error during deleteBucket");
        }
    }

    @Blocking
    public void putObjectOnBucket(String bucketName, String objectName, InputStream stream, long size,
            String mimeType) {
        createBucket(bucketName);
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(objectName)
                    .stream(stream, size, -1)
                    .contentType(mimeType).build());
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            throw new MinioException("Error during putObjectBucket");
        }
    }

    @Blocking
    public byte[] getObjectFromBucket(String bucketName, String objectName) {
        try {
            InputStream objectStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            byte[] content = IOUtils.toByteArray(objectStream);
            objectStream.close();
            return content;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            throw new MinioException("Error during getObjectFromBucket");
        }
    }

    @Blocking
    public void removeObjectFromBucket(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            if (ex.getMessage().equals("The specified bucket does not exist")) {
                return;
            }
            throw new MinioException("Error during removeObjectFromBucket");
        }
    }

}
