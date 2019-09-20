FROM openjdk:11-jre-slim

# add builded application in form of executable JAR archive to container
ADD target/dog-station-1-SNAPSHOT.jar dog-station-1-SNAPSHOT.jar

# add properties file to container
ADD src/main/resources/application.properties application.properties

ENTRYPOINT ["java","-jar","/dog-station-1-SNAPSHOT.jar"]

EXPOSE 8080
