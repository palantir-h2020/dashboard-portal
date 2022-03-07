package eu.palantir.portal.websocket.encoder;

import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.palantir.portal.dto.message.FrontendNotification;

public class FrontendNotificationEncoder implements Encoder.Text<FrontendNotification> {

    private static final Logger LOG = Logger.getLogger(FrontendNotificationEncoder.class.getName());

    private static ObjectMapper ObjMapper = new ObjectMapper();

    @Override
    public String encode(FrontendNotification object) throws EncodeException {
        try {
            return ObjMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.severe("JSON Processing error during encoding: " + object);
            throw new EncodeException(object, "JSON Processing error: " + e.getMessage());
        }
    }

    @Override
    public void init(EndpointConfig config) {
        // Custom initialization logic

    }

    @Override
    public void destroy() {
        // Close resources

    }

}
