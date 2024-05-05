FROM openjdk:21

LABEL version="0.0.1" \
  org.opencontainers.image.source="https://github.com/0xzer0x/inventory-management" \
  org.opencontainers.image.description="An inventory management system using Spring MVC with Thymeleaf and Bootstrap CSS."

ADD build/libs/inventory-management-0.0.1.jar /app/

WORKDIR /app

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "./inventory-management-0.0.1.jar" ]
