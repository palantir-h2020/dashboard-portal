package eu.ubitech.palantir.portal.service;

import io.minio.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import eu.ubitech.palantir.portal.exceptions.MinioException;

@ApplicationScoped
public class StorageService {

  private static final Logger logger = Logger.getLogger(StorageService.class.getName());

  @ConfigProperty(name = "minio.url")
  private String minioEndpoint;

  @ConfigProperty(name = "minio.access-key")
  private String minioAccessKey;

  @ConfigProperty(name = "minio.secret-key")
  private String minioSecretKey;

  public synchronized void createBucket(String bucketName) {
    try {
      MinioClient minioClient = MinioClient.builder().endpoint(minioEndpoint)
          .credentials(minioAccessKey, minioSecretKey).build();
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

  public synchronized void deleteBucket(String bucketName) {
    try {
      logger.info("Deleting min.io client with bucketName: " + bucketName);
      MinioClient minioClient = MinioClient.builder().endpoint(minioEndpoint)
          .credentials(minioAccessKey, minioSecretKey).build();
      boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (isExist) {
        Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        // remove non empty bucket
        for (Result<Item> object : objects) {
          minioClient
              .removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(object.get().objectName()).build());
        }
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
      }
    } catch (Exception ex) {
      logger.severe(ex.getMessage());
      throw new MinioException("Error during deleteBucket");
    }
  }

  public void putObjectOnBucket(String bucketName, String objectName, InputStream stream, long size, String mimeType) {
    MinioClient minioClient = MinioClient.builder().endpoint(minioEndpoint).credentials(minioAccessKey, minioSecretKey)
        .build();
    createBucket(bucketName);
    try {
      minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1)
          .contentType(mimeType).build());
    } catch (Exception ex) {
      logger.severe(ex.getMessage());
      throw new MinioException("Error during putObjectBucket");
    }
  }

  public byte[] getObjectFromBucket(String bucketName, String objectName) {
    try {
      MinioClient minioClient = MinioClient.builder().endpoint(minioEndpoint)
          .credentials(minioAccessKey, minioSecretKey).build();
      InputStream objectStream = minioClient
          .getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
      byte[] content = IOUtils.toByteArray(objectStream);
      objectStream.close();
      return content;
    } catch (Exception ex) {
      logger.severe(ex.getMessage());
      throw new MinioException("Error during getObjectFromBucket");
    }
  }

  public void removeObjectFromBucket(String bucketName, String objectName) {
    try {
      MinioClient minioClient = MinioClient.builder().endpoint(minioEndpoint)
          .credentials(minioAccessKey, minioSecretKey).build();
      minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    } catch (Exception ex) {
      logger.severe(ex.getMessage());
      throw new MinioException("Error during removeObjectFromBucket");
    }
  }

}
