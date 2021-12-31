#Any library that is not published in the Maven Central should be stored here in order to be deployed manually
mvn install:install-file -Dfile=ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc  -Dversion=6.1 -Dpackaging=jar
