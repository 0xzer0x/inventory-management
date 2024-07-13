FROM eclipse-temurin:21-jdk AS build

ADD . /app/

RUN /app/gradlew --project-dir=/app bootJar

FROM eclipse-temurin:21-jre-alpine AS final

LABEL version="0.2" \
  org.opencontainers.image.source="https://github.com/0xzer0x/inventory-management" \
  org.opencontainers.image.description="An inventory management system using Spring MVC with Thymeleaf and Bootstrap CSS."

ENV SERVER_PORT=80 SPRING_PROFILES_ACTIVE=prod

COPY --from=build /app/build/libs/inventory-management-0.2.jar /app/

EXPOSE 80

ENTRYPOINT [ "java", "-jar", "/app/inventory-management-0.2.jar" ]
