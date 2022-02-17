#Stage 1: Build Maven Application
FROM maven:3.8.4-jdk-11 AS build
WORKDIR /home/app
COPY . .
RUN mvn clean package

#Stage 2: Deploy Maven App
FROM openjdk:8-jdk-alpine
COPY --from=build home/app/server/target/jtqj-server-bootified.war jtq.war
ENTRYPOINT ["java","-jar","/jtq.war"]