# Imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio dentro del contenedor
WORKDIR /app

# Copiar jar al contenedor
COPY target/userapi-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar
ENTRYPOINT ["java","-jar","app.jar"]
