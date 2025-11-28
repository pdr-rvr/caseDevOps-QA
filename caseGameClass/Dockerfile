# Usamos uma imagem Alpine com JRE 17
FROM eclipse-temurin:17-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Atualiza os pacotes para as versões mais recentes
RUN apk update && apk upgrade

# Cria um grupo e um usuário 'spring' (Hardening)
RUN addgroup -S spring && adduser -S spring -G spring

# Copia o JAR
COPY target/caseGameClass-0.0.1-SNAPSHOT.jar app.jar

# Define usuário não-root
USER spring

# Expõe a porta
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
