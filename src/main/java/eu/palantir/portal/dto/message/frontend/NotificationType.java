package eu.palantir.portal.dto.message.frontend;

public enum NotificationType {

    ACTION {
        // overriding toString() for SMALL
        public String toString() {
            return "action";
        }
    },

    NOTIFICATION {
        public String toString() {
            return "notification";
        }
    },
}
