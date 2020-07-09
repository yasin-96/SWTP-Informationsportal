FROM openjdk:11-jre AS build-env
WORKDIR /usr/local
COPY build/libs/*.jar /usr/local/app.jar
RUN jar -xf *.jar

FROM openjdk:11-jre
#COPY build/libs/*.jar /usr/local/app.jar
COPY --from=build-env /usr/local/BOOT-INF/lib /usr/local/app/lib
COPY --from=build-env /usr/local/META-INF /usr/local/app/META-INF
COPY --from=build-env /usr/local/BOOT-INF/classes /usr/local/app

WORKDIR /usr/local
EXPOSE 8080

ENTRYPOINT exec java -cp app:app/lib/* de.thm.swtp.information_portal.InformationPortal $JAVA_OPTS