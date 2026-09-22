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

Desde una terminal, con el servidor arrancado:

```bash
mysql -u root -p -h 127.0.0.1 -P 3306
```

Si te pide la contrasena y te deja entrar (`mysql>`), todo esta listo para el siguiente
paso. No hace falta crear la base de datos `coche_db` a mano, la aplicacion la crea sola.

## Configura tu base de datos

La aplicacion se conecta a MySQL en `localhost:3306`, a una base de datos llamada
`coche_db` que se **crea sola** la primera vez que arrancas la app (no hace falta
crearla a mano). Lo unico que necesitas es decirle a la aplicacion **tu** usuario y
password de MySQL, sin escribirlos dentro del codigo que se sube a git.

Tienes dos formas de hacerlo, elige la que prefieras:

### Opcion A (recomendada en clase): archivo `application-local.properties`

1. Copia la plantilla:
   ```bash
   cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
   ```
2. Abre `src/main/resources/application-local.properties` y pon tu usuario y password reales:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=tu-password-de-mysql
   ```
3. Listo. Este archivo esta en `.gitignore`, asi que **nunca se sube al repositorio** y
   cada alumno mantiene sus propias credenciales sin pisar las de sus companeros.

### Opcion B: variables de entorno

Si no quieres crear el archivo, puedes exportar las variables antes de arrancar:

```bash
export DB_USERNAME=root
export DB_PASSWORD=tu-password-de-mysql
./mvnw spring-boot:run
```

> Si no configuras nada, la aplicacion intenta conectarse con `root` / `root` (valores
> por defecto genericos definidos en `application.properties`).

## Como ejecutar

Con MySQL corriendo en tu maquina (`localhost:3306`) y las credenciales configuradas
como se explica arriba:

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
