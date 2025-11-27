# Usamos uma imagem Alpine (mais leve e segura) com JRE 17
FROM eclipse-temurin:17-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Cria um grupo e um usuário 'spring' para não rodar como root (Hardening)
RUN addgroup -S spring && adduser -S spring -G spring

# Copia o JAR gerado (ajuste o caminho se necessário, mas baseado no seu pom.xml é este)
COPY target/caseGameClass-0.0.1-SNAPSHOT.jar app.jar

# Define que o container rodará com o usuário 'spring'
USER spring

# Expõe a porta
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
