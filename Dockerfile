# 선택한 기반 이미지
FROM openjdk:11-jdk

# 애플리케이션 디렉토리 생성
WORKDIR /app

# 애플리케이션 JAR 파일을 컨테이너로 복사
COPY build/libs/your-app.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
