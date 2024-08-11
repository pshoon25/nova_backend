FROM bellsoft/liberica-openjdk-alpine:17

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

# 이전에 복사된 JAR 파일이 있다면 삭제
RUN rm -f /app.jar

# 새 JAR 파일을 컨테이너에 복사
COPY ${JAR_FILE} /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
