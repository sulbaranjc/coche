# Coche — Proyecto didactico Spring Boot

Aplicacion de ejemplo para aprender a construir un **monolito con Java y Spring Boot**:
un CRUD completo (Crear, Listar, Editar, Eliminar) sobre la entidad `Coche`, con capas
de Controller, Service y Repository, vistas con Thymeleaf y Bootstrap 5, y persistencia
en MySQL.

## Tecnologias

- Java 21
- Spring Boot 4.1 (Web MVC, Data JPA, Validation, Thymeleaf)
- MySQL 8
- Bootstrap 5.3 (via CDN, sin CSS propio)
- Maven (con wrapper `mvnw` incluido, no hace falta tener Maven instalado)

## Estructura del proyecto

```
src/main/java/com/example/coche/
├── model/         Coche (entidad JPA), Combustible y Transmision (enums)
├── repository/    CocheRepository (Spring Data JPA)
├── service/       CocheService (interfaz) + service/impl/CocheServiceImpl
├── controller/    CocheController (CRUD), HomeController
└── exception/     Excepcion de "coche no encontrado" + manejador global

src/main/resources/
├── schema.sql     Crea la tabla 'coche'
├── data.sql       Inserta 10 coches de ejemplo
└── templates/     Vistas Thymeleaf (listado y formulario) + layout compartido
```

## Instalar MySQL

Necesitas un servidor MySQL corriendo en tu maquina (version 8 recomendada). Elige las
instrucciones de tu sistema operativo:

### Ubuntu / Debian

```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl enable --now mysql
```

Configura la contrasena del usuario `root` (o crea un usuario propio, ver mas abajo):

```bash
sudo mysql
```

Dentro del cliente de MySQL:

```sql
ALTER USER 'root'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'tu-password';
FLUSH PRIVILEGES;
EXIT;
```

Comprueba que aceptas conexiones por red (la app se conecta por TCP a `localhost:3306`,
no solo por socket). Edita `/etc/mysql/mysql.conf.d/mysqld.cnf` y confirma que tienes:

```
bind-address = 127.0.0.1
```

y que **no** existe una linea `skip-networking`. Si haces algun cambio, reinicia el
servicio con `sudo systemctl restart mysql`.

### macOS (con Homebrew)

```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

`mysql_secure_installation` te guia para poner contrasena al usuario `root` y asegurar
la instalacion.

### Windows

Descarga el **MySQL Installer** desde la pagina oficial
(https://dev.mysql.com/downloads/installer/), elige "Server only" (o "Developer Default"
si tambien quieres MySQL Workbench) y sigue el asistente. Durante la instalacion te pedira
definir la contrasena del usuario `root`; anotala, la necesitaras en el siguiente paso.

### Verifica que MySQL funciona

Desde una terminal (en Windows, la ventana de **cmd**), con el servidor arrancado:

```
mysql -u root -p -h 127.0.0.1 -P 3306
```

Si te pide la contrasena y te deja entrar (`mysql>`), todo esta listo para el siguiente
paso.

> En Windows, si `cmd` no reconoce el comando `mysql` ("no se reconoce como un comando
> interno o externo"), es que la carpeta `bin` de MySQL no esta en el PATH. Búscala
> (normalmente `C:\Program Files\MySQL\MySQL Server 8.0\bin`) y usa la ruta completa,
> por ejemplo:
> ```
> "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p -h 127.0.0.1 -P 3306
> ```

## Crea el usuario de la aplicacion

Por buena practica, la aplicacion **no** deberia conectarse con el usuario `root` de
MySQL (que tiene permisos sobre todo el servidor), sino con un usuario propio que solo
pueda tocar la base de datos `coche_db`. Vamos a crearlo.

Entra a MySQL como `root` (mismo comando de antes) y, dentro del prompt `mysql>`,
ejecuta:

```sql
CREATE DATABASE IF NOT EXISTS coche_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'coche_app'@'localhost' IDENTIFIED BY 'coche_app_pwd';
GRANT ALL PRIVILEGES ON coche_db.* TO 'coche_app'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

Esto crea:
- La base de datos `coche_db` (aunque la aplicacion tambien sabe crearla sola si no
  existe, hacerlo aqui te asegura que el usuario nuevo tiene permisos sobre ella desde
  el principio).
- Un usuario `coche_app` con password `coche_app_pwd` (cambia esa password por la que
  quieras) que **solo** tiene permisos sobre `coche_db`, no sobre el resto del servidor.

Este es el usuario y password que vas a usar en el siguiente paso, no `root`.

## Configura tu base de datos

La aplicacion se conecta a MySQL en `localhost:3306`, a la base de datos `coche_db` que
creaste en el paso anterior. Ahora hay que decirle a la aplicacion el usuario
`coche_app` y su password, sin escribirlos dentro del codigo que se sube a git.

Tienes dos formas de hacerlo, elige la que prefieras:

### Opcion A (recomendada en clase): archivo `application-local.properties`

1. Copia la plantilla.

   En Windows (**cmd**):
   ```
   copy src\main\resources\application-local.properties.example src\main\resources\application-local.properties
   ```

   En macOS / Linux:
   ```bash
   cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
   ```

2. Abre `src/main/resources/application-local.properties` (con el Bloc de notas, VS Code,
   IntelliJ...) y pon el usuario y password que creaste antes:
   ```properties
   spring.datasource.username=coche_app
   spring.datasource.password=coche_app_pwd
   ```
3. Listo. Este archivo esta en `.gitignore`, asi que **nunca se sube al repositorio** y
   cada alumno mantiene sus propias credenciales sin pisar las de sus companeros.

### Opcion B: variables de entorno

Si no quieres crear el archivo, puedes definir las variables antes de arrancar.

En Windows (**cmd**) — ojo, `set` solo dura mientras esa ventana de cmd este abierta:
```
set DB_USERNAME=coche_app
set DB_PASSWORD=coche_app_pwd
mvnw.cmd spring-boot:run
```

En macOS / Linux:
```bash
export DB_USERNAME=coche_app
export DB_PASSWORD=coche_app_pwd
./mvnw spring-boot:run
```

> Si no configuras nada, la aplicacion intenta conectarse con `root` / `root` (valores
> por defecto genericos definidos en `application.properties`).

## Como ejecutar

Con MySQL corriendo en tu maquina (`localhost:3306`) y las credenciales configuradas
como se explica arriba:

En Windows (**cmd**), desde la carpeta del proyecto:
```
mvnw.cmd spring-boot:run
```

En macOS / Linux:
```bash
./mvnw spring-boot:run
```

La app arranca en **http://localhost:8081/coches** (el puerto se configura en
`application.properties` con `server.port`).

Al arrancar por primera vez, `schema.sql` crea la tabla `coche` y `data.sql` inserta
10 coches de ejemplo automaticamente.

## Flujo del CRUD

| Accion            | Ruta                       | Metodo |
|--------------------|-----------------------------|--------|
| Listar coches       | `/coches`                  | GET    |
| Formulario alta      | `/coches/nuevo`             | GET    |
| Formulario edicion    | `/coches/editar/{id}`         | GET    |
| Guardar (crear/editar) | `/coches/guardar`            | POST   |
| Eliminar            | `/coches/eliminar/{id}`        | POST   |

## Problemas frecuentes

### `Public Key Retrieval is not allowed`

Si al arrancar la aplicacion falla con este error:

```
java.sql.SQLNonTransientConnectionException: Public Key Retrieval is not allowed
```

es porque MySQL 8 autentica por defecto con `caching_sha2_password` y, al conectarnos
sin SSL (`useSSL=false`), el driver necesita pedirle al servidor su clave publica para
enviar la password cifrada. Eso esta bloqueado salvo que se permita de forma explicita.

La URL de conexion de `application.properties` ya incluye el parametro que lo permite:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/coche_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

Si ves este error, comprueba que tu URL tiene `allowPublicKeyRetrieval=true` (por
ejemplo, si la has sobrescrito en tu `application-local.properties`).

> `allowPublicKeyRetrieval=true` es adecuado para desarrollo en local. En produccion lo
> correcto es usar SSL en la conexion en lugar de desactivarlo.
