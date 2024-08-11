FROM bellsoft/liberica-openjdk-alpine:17

CMD ["./gradlew", "clean", "build"]

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:mysql://192.168.1.199:3306/NOVA_LIVE?serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=su
ENV SPRING_DATASOURCE_PASSWORD=tjdgns123..

ENTRYPOINT ["java","-jar","/app.jar"]