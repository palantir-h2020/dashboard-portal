package eu.palantir.portal.rest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import eu.palantir.portal.dto.message.ti.ThreadFindingSysLog;
import eu.palantir.portal.kafka.processing.PalantirNotificationsGateway;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Map;
import java.util.logging.Logger;

@Path("/")
public class GatewayResource {

    private static final String FALLBACK_RESOURCE = "/META-INF/resources/index.html";
    private static final Map<String, String> EXTENSION_TYPES = Map.of("svg", "image/svg+xml");
    private final ApiResource apiResource;

    private final PalantirNotificationsGateway notificationsGateway;

    private static final Logger logger = Logger.getLogger(GatewayResource.class.getName());

    @Inject
    public GatewayResource(ApiResource apiResource, PalantirNotificationsGateway notificationsGateway) {
        this.apiResource = apiResource;
        this.notificationsGateway = notificationsGateway;
    }

    @Path("/api/v1")
    public ApiResource getApiResource() {
        return apiResource;
    }

    // @GET
    // @Path("/")
    // public Response getFrontendRoot() throws IOException {
    // return getFrontendStaticFile("index.html");
    // }

    @GET
    @Path("/test_threatfinding")
    public Response sendThreatFinding() {
        ThreadFindingSysLog threadFindingSysLog = new ThreadFindingSysLog(
                "Mar  4 18:01:44 mail HORDE: Guest user is not authorized for Horde (Host: 192.168.10.81). [pid 6193 on line 324 of \"/usr/share/php/Horde/Registry.php\"]",
                "nikto",
                0.9532062624908422, "0.6513663945875967", "10.101.41.33");

        notificationsGateway.acceptThreadFindingSysLog(threadFindingSysLog);

        return Response.ok().build();
    }

    @GET
    @Path("/{fileName:.+}")
    public Response getFrontendStaticFile(@PathParam("fileName") String fileName) throws IOException {
        final InputStream requestedFileStream = GatewayResource.class
                .getResourceAsStream("/META-INF/resources/" + fileName);
        final InputStream inputStream;
        final String fileToServe;
        if (requestedFileStream != null) {
            fileToServe = fileName;
            inputStream = requestedFileStream;
        } else {
            fileToServe = FALLBACK_RESOURCE;
            inputStream = GatewayResource.class.getResourceAsStream(FALLBACK_RESOURCE);
        }
        final StreamingOutput streamingOutput = outputStream -> IOUtils.copy(inputStream, outputStream);
        String contentType = contentType(inputStream, fileToServe);
        if (fileName.contains("css/")) {
            contentType = "text/css";
        }
        return Response.ok(streamingOutput).type(contentType).build();
    }

    private String contentType(InputStream inputStream, String file) throws IOException {
        return EXTENSION_TYPES.getOrDefault(FilenameUtils.getExtension(file),
                URLConnection.guessContentTypeFromStream(inputStream));
    }
}
