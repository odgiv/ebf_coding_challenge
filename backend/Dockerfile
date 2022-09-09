FROM eclipse-temurin:17-jdk-focal

RUN apt-get update 
RUN apt-get install -y inotify-tools dos2unix
WORKDIR /srv/app/backend

# RUN ./mvnw dependency:go-offline

CMD ["./mvnw","spring-boot:run"]