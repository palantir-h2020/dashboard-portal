package eu.palantir.portal.model;

public enum PalantirDeploymentModel {

    CLOUD {
        public String toString() {
            return "cloud";
        }
    },

    MEC {
        public String toString() {
            return "mec";
        }
    },

    VCPE {
        public String toString() {
            return "vcpe";
        }
    },

}
