FROM openjdk:17-jdk-alpine
RUN apk add --no-cache tzdata
ENV TZ=America/Guayaquil
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app
RUN chown -R spring:spring /app
RUN chmod 755 /app
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/weather-api-1.0.jar
ENTRYPOINT ["java","-jar","/app/weather-api-1.0.jar"]