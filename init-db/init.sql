-- Create tables if not exists
CREATE TABLE IF NOT EXISTS franchise (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS branch (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    franchise_id BIGINT,
    FOREIGN KEY (franchise_id) REFERENCES franchise(id)
);

CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    branch_id BIGINT,
    FOREIGN KEY (branch_id) REFERENCES branch(id)
);

-- Insert data
INSERT IGNORE INTO franchise (id, name) VALUES
(1, 'Franchise Norte'),
(2, 'Franchise Centro');

INSERT IGNORE INTO branch (id, name, franchise_id) VALUES
(1, 'Sucursal Medellin', 1),
(2, 'Sucursal Bogota', 1),
(3, 'Sucursal Cali', 2);

INSERT IGNORE INTO product (id, name, stock, branch_id) VALUES
(1, 'Hamburguesa', 50, 1),
(2, 'Pizza', 30, 1),
(3, 'Hot Dog', 20, 2),
(4, 'Papas Fritas', 40, 3);