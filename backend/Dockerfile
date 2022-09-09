FROM eclipse-temurin:17-jdk-focal

RUN apt-get update 
WORKDIR /srv/app/backend

RUN cat ./mvnw | tr -d '\r' > ./mvnw

CMD ["./mvnw","spring-boot:run"]