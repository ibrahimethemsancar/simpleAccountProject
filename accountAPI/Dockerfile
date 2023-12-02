FROM openjdk:19 AS build

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN sed -i 's/\r$//' mvnw
RUN /bin/sh mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:19
WORKDIR /account
COPY --from=build /target/*.jar account.jar
ENTRYPOINT ["java", "-jar", "account.jar"]
