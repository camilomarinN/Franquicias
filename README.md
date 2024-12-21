# Franquicias
Desarrollo API Franquicias
# Pre requisitos
- Se debe de poseer un IDE de desarrollo para JAVA pues este es una API REST con SpringBoot, la cual se encuentra en la versión 21 de JDK la cual también se debe de tener instalada en el sistema. (comprobar desde CMD java --version)
- Una vez descargado y abierto el proyecto en tu PC local, este se debe de ejecutar en consola el comando "mvn clean install" para descargar todas las dependencias requeridas por el proyecto para funcionar correctamente.
- Esta aplicación no tiene configuraciones personalizadas para facilitar su uso, esta se ejecuta en el puerto 8080 y el acceso al SWAGGER UI (documetación de la API) se encuentra en la ruta "http://localhost:8080/swagger-ui/index.html"
- Se está utilizando como conexión una BD de MongoDB Altlas, la cual posee un acceso de IP publico y el usuario ya se encuentra configurado en el archivo respectivo Application.properties.

# Ejecución
Si los pre requisitos son validados correctamente se puede ejecutar el proyecto simplemente ejecuntando el archivo "FranquiciasApplication" el cual es el ejecutable predeterminado para un proyecto SpringBoot.
