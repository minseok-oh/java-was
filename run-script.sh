./gradlew build
docker build --no-cache -t java-was .
docker run -d -p 8080:8080 java-was