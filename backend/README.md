# Car Center - "Backend con Spring Boot JWT"

![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)

## Instalación
El proyecto se crea con Maven, por lo que solo necesita importarlo a su IDE y construir el proyecto para resolver las dependencias

## Configuración de la base de datos

Crear una base de datos MySQL con el nombre `car_center` y agregue las credenciales a `/resources/application.properties.`
Los predeterminados son:

```
spring.datasource.url=jdbc:mysql://localhost:3306/car_center
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

## Uso

Ejecutar el proyecto a través del IDE y diríjase a http: // localhost: 8080

o

ejecutar el comando en la línea de comando:

```
mvn spring-boot:run
```