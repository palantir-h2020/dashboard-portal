package eu.palantir.portal.dto.message.frontend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum NotificationType {

    ACTION {
        // overriding toString() for SMALL
        @JsonValue
        public String toString() {
            return "action";
        }
    },

    INCIDENT {
        @JsonValue
        public String toString() {
            return "incident";
        }
    },

    NOTIFICATION {
        @JsonValue
        public String toString() {
            return "notification";
        }
    },
}
