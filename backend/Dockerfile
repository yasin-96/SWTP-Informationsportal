FROM maven:latest AS build-env
COPY ./target/*.jar /usr/local/app.jar
WORKDIR /usr/local
RUN jar -xf *.jar

FROM maven:latest
COPY --from=build-env /usr/local/BOOT-INF/lib /usr/local/app/lib
COPY --from=build-env /usr/local/META-INF /usr/local/app/META-INF
COPY --from=build-env /usr/local/BOOT-INF/classes /usr/local/app

WORKDIR /usr/local
EXPOSE 9000

ENTRYPOINT exec java -cp app:app/lib/* de.thm.swtp.information_portal.InformationPortal $JAVA_OPTS