# Usar a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Adicionar o jar da aplicação
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expor a porta que o Spring Boot está escutando
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
