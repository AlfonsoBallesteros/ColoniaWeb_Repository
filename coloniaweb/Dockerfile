     
FROM maven:3.6.3-jdk-11-slim AS builder
WORKDIR /workspace
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package -Dmaven.test.skip=true

FROM openjdk:11-jdk-slim
COPY --from=builder /workspace/target/*.jar coloniaweb.jar
EXPOSE 8080
CMD ["java", "-jar", "/coloniaweb.jar"]