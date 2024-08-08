FROM openjdk:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /homepage/orummedia/backend

# JAR 파일을 컨테이너로 복사
COPY build/libs/orummmedia-backend-0.0.1-SNAPSHOT.jar /app/orummmedia-backend.jar

# Expose port 8080
EXPOSE 8080

# 컨테이너에서 애플리케이션을 실행하는 명령어
CMD ["java", "-jar", "/app/orummmedia-backend.jar"]
