FROM openjdk:21

LABEL version="0.0.1" \
  description="An inventory management system using Spring MVC with Thymeleaf and Bootstrap CSS."

ADD . /app

WORKDIR /app

RUN ./gradlew bootJar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/build/libs/inventory-management-0.0.1.jar" ]
