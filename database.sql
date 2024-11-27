CREATE DATABASE gravadoradb;

USE gravadoradb;

CREATE TABLE musicas (
              id INT AUTO_INCREMENT NOT NULL,
              nome VARCHAR(100),
              artista VARCHAR(100),
              album VARCHAR(100),
              dataLancamento DATE,
              PRIMARY KEY(id)
);