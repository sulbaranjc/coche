-- Script de creacion de la tabla 'coche'
-- La base de datos 'coche_db' se crea automaticamente por el driver JDBC
-- (parametro createDatabaseIfNotExist=true en application.properties)

CREATE TABLE IF NOT EXISTS coche (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca             VARCHAR(50)    NOT NULL,
    modelo            VARCHAR(50)    NOT NULL,
    matricula         VARCHAR(15)    NOT NULL UNIQUE,
    anio_fabricacion  INT            NOT NULL,
    color             VARCHAR(30)    NOT NULL,
    precio            DECIMAL(10,2)  NOT NULL,
    kilometraje       INT            NOT NULL,
    combustible       VARCHAR(20)    NOT NULL,
    transmision       VARCHAR(20)    NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
