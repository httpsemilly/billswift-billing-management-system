CREATE TABLE clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    state VARCHAR(50) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    zip_code VARCHAR(50) NOT NULL,
    city VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    idCompany VARCHAR(50),
    FOREIGN KEY (idCompany) REFERENCES companies(id)
);