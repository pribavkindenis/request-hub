FROM openjdk:11
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM openjdk:11
WORKDIR /app
COPY --from=0 /app/build/libs/request-hub-backend-0.1.jar .
COPY wait-for-it.sh .