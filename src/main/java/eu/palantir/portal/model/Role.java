package eu.palantir.portal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Role {

    ADMIN {
        @JsonValue
        public String toString() {
            return "admin";
        }
    },

    NETWORK_OPERATOR {
        @JsonValue
        public String toString() {
            return "network_operator";
        }
    },

    SME_MANAGER {
        @JsonValue
        public String toString() {
            return "sme_manager";
        }
    },

    SC_DEVELOPER {
        @JsonValue
        public String toString() {
            return "sc_developer";
        }
    },

    INTEGRITY_ASSURANCE {
        @JsonValue
        public String toString() {
            return "integrity_assurance";
        }
    }

}
