CREATE TABLE clients (
    id VARCHAR(50) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    idCompany VARCHAR,
    FOREIGN KEY (idCompany) REFERENCES companies(id)
);