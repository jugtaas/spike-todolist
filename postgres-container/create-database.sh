#!/bin/bash

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE ROLE jugtaas WITH LOGIN ENCRYPTED PASSWORD 'saatguj2016';
    ALTER ROLE jugtaas CREATEROLE CREATEDB;
    SET ROLE jugtaas;
    CREATE DATABASE jugtaas OWNER jugtaas;
    GRANT ALL PRIVILEGES ON DATABASE jugtaas TO jugtaas;
    \connect jugtaas jugtaas;
    CREATE TABLE IF NOT EXISTS todo (
        id bigserial PRIMARY KEY,
        text varchar(1024) NOT NULL UNIQUE,
        status varchar(20) NOT NULL,
        created timestamp NOT NULL,
        done timestamp
     );
EOSQL

# docker build . --rm -t todo-postgresql