# 베이스 이미지 지정 (JDK 사용)
FROM openjdk:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너로 복사
COPY build/libs/myapp.jar /app/myapp.jar

# 컨테이너에서 애플리케이션을 실행하는 명령어
CMD ["java", "-jar", "/app/myapp.jar"]
