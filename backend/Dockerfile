FROM maven:latest AS build-env
WORKDIR /usr/local
RUN ls -lsa ./
#COPY backend/target/*.jar /usr/local/app.jar
RUN jar -xf *.jar

FROM maven:latest
#COPY build/libs/*.jar /usr/local/app.jar
COPY --from=build-env /usr/local/BOOT-INF/lib /usr/local/app/lib
COPY --from=build-env /usr/local/META-INF /usr/local/app/META-INF
COPY --from=build-env /usr/local/BOOT-INF/classes /usr/local/app

WORKDIR /usr/local
EXPOSE 8080

ENTRYPOINT exec java -cp app:app/lib/* de.thm.swtp.information_portal.InformationPortal $JAVA_OPTS
