# STAGE1: Build the jar file

FROM openjdk:12-alpine as build
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN ./mvnw clean test package

# STAGE2: Run the jar file

FROM build as runtime
COPY --from=build /app/source/target/*.jar /app/app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
