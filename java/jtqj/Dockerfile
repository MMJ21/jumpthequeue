FROM openjdk:8-jdk-alpine
COPY server/target/jtqj-server-bootified.war jtq.war
ENTRYPOINT ["java","-jar","/jtq.war"]