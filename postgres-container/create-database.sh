#!/bin/bash

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER jugtaas;
    CREATE DATABASE jugtaas;
    GRANT ALL PRIVILEGES ON DATABASE jugtaas TO jugtaas;
    \connect jugtaas;
    CREATE TYPE todo_status AS ENUM ('CREATED', 'WORK_IN_PROGRES', 'SUSPENDED', 'DONE');
    CREATE TABLE IF NOT EXISTS todo (
        id bigserial PRIMARY KEY,
        text character(1024) NOT NULL UNIQUE,
        status todo_status NOT NULL,
        created timestamp NOT NULL,
        done timestamp
     );
EOSQL
