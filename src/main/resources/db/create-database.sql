CREATE DATABASE IF NOT EXISTS machadao
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE machadao;

CREATE TABLE IF NOT EXISTS fictional_relics
(
    id                    BIGINT         NOT NULL AUTO_INCREMENT,
    code                  VARCHAR(80)    NOT NULL,
    name                  VARCHAR(120)   NOT NULL,
    description           VARCHAR(500)   NOT NULL,
    illustrative_value_usd DECIMAL(12, 2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_fictional_relics_code UNIQUE (code)
);
