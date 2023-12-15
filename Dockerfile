# Step 1: Package the spring-boot app into .jar file
FROM maven:3.8.5-openjdk-17 AS packaging
WORKDIR /api-movie
COPY . .
RUN mvn clean install

# Step 2: 
### Optimization image package
FROM eclipse-temurin:17-jre-alpine AS builder
COPY --from=packaging /api-movie/target/movie-api-0.0.1-SNAPSHOT.jar ./app.jar
#COPY ./target/movie-api-0.0.1-SNAPSHOT.jar ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

# Step 3:
FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /dependencies/ ./
COPY --from=builder /spring-boot-loader/ ./
COPY --from=builder /snapshot-dependencies/ ./
COPY --from=builder /application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
EXPOSE 8080