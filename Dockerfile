FROM adoptopenjdk:8-jre
WORKDIR /app
COPY ./target/api-loteria-0.0.1-SNAPSHOT.war apiloteria.war
ENTRYPOINT  ["java", "-jar", "-Dspring.datasource.url=jdbc:postgresql://${ADDRESS_POSTGRES}:5432/loteria_api", "apiloteria.war"]
EXPOSE 8080