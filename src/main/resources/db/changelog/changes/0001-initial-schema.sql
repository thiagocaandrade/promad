-- changeset thiagocaandrade:1
CREATE SEQUENCE processo_seq START 1;
CREATE TABLE processo (
    id BIGINT PRIMARY KEY DEFAULT nextval('processo_seq')
);
-- rollback not required

-- changeset thiagocaandrade:2
CREATE SEQUENCE reu_seq START 1;
CREATE TABLE reu (
    id BIGINT PRIMARY KEY DEFAULT nextval('reu_seq'),
    nome VARCHAR(255) UNIQUE NOT NULL
);
-- rollback not required

-- changeset thiagocaandrade:3
ALTER TABLE processo ADD COLUMN reu_id BIGINT;
ALTER TABLE processo ADD CONSTRAINT fk_processo_reu FOREIGN KEY (reu_id) REFERENCES reu(id);
-- rollback not required

-- changeset thiagocaandrade:4
CREATE TABLE processo_numeros (
    processo_id BIGINT NOT NULL,
    numero BIGINT NOT NULL,
    PRIMARY KEY (processo_id, numero),
    FOREIGN KEY (processo_id) REFERENCES processo(id)
);
-- rollback not required