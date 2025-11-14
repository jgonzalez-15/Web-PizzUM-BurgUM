INSERT INTO usuario (email, nombre, apellido, contrasenia, telefono, fecha_nac, cedula)
VALUES
    ('juan@example.com', 'Juan', 'Pérez', '12345678', 59891234567, '1990-05-15', 12345678),
    ('maria@example.com', 'María', 'Gómez', 'abcd', 59899887766, '1995-11-02', 87654321),
    ('pepe@example.com', 'Pepe', 'Rodríguez', 'admin123', 59891112222, '1988-01-20', 55555555);

INSERT INTO cliente (email)
VALUES ('juan@example.com'), ('maria@example.com');

INSERT INTO administrador (email)
VALUES ('pepe@example.com');

INSERT INTO domicilio (direccion, esta_activo)
VALUES
    ('Av. Italia 1234, Montevideo', true),
    ('Bvar. Artigas 567, Montevideo', true),
    ('Ellauri 900, Montevideo', true);

INSERT INTO cliente_domicilio (id_cliente, id_domicilio)
VALUES
    ('juan@example.com', 1),
    ('juan@example.com', 3),
    ('maria@example.com', 2);

INSERT INTO medio_de_pago (numero_tarjeta, fecha_vencimiento, nombre_titular, esta_activo, cliente_id)
VALUES
    (4111111111111111, '2027-05-31', 'Juan Pérez', true, 'juan@example.com'),
    (5555444433332222, '2026-08-30', 'María Gómez', true, 'maria@example.com');


-- Masas
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Masa', 'Masa Tradicional', false, 50, true, true),
    ('Masa', 'Masa Integral', true, 60, true, true),
    ('Masa', 'Masa Fina', false, 55, true, true);

-- Salsas
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Salsa', 'Salsa de Tomate', true, 30, true, true),
    ('Salsa', 'Salsa Blanca', true, 35, true, true);

-- Toppings
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Topping', 'Jamón', false, 40, true, true),
    ('Topping', 'Queso Mozzarella', true, 45, true, true),
    ('Topping', 'Aceitunas', true, 25, true, true);

-- Hamburguesas
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Hamburguesa', 'Clásica', false, 150, true, true),
    ('Hamburguesa', 'Vegetariana', true, 160, true, true);

-- Ingredientes Hamburguesa
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Ingrediente', 'Lechuga', true, 10, true, true),
    ('Ingrediente', 'Tomate', true, 10, true, true),
    ('Ingrediente', 'Queso Cheddar', false, 20, true, true);

--Bebidas
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Bebida', 'Coca-Cola 500ml', true, 80, true, true),
    ('Bebida', 'Agua Mineral 600ml', true, 60, true, true),
    ('Bebida', 'Cerveza 355ml', true, 100, true, true),
    ('Bebida', 'Jugo de Naranja', true, 70, true, true),
    ('Bebida', 'Sprite 500ml', true, 80, true, true);

--Panes
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Pan', 'Pan Clásico', false, 50, true, true),
    ('Pan', 'Pan Brioche', false, 60, true, true),
    ('Pan', 'Pan Sin Gluten', true, 70, true, true);

--Hamburguesas
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Salsa_Hamburguesa', 'Mayonesa', true, 20, true, true),
    ('Salsa_Hamburguesa', 'Ketchup', true, 15, true, true),
    ('Salsa_Hamburguesa', 'Mostaza', true, 15, true, true),
    ('Salsa_Hamburguesa', 'Salsa BBQ', true, 25, true, true);

--Tamanios Pizza
INSERT INTO producto (tipo, nombre, sin_tacc, precio, esta_activo, visible)
VALUES
    ('Tamanio', 'Chico', true, 50, true, true),
    ('Tamanio', 'Mediano', true, 100, true, true),
    ('Tamanio', 'Grande', true, 150, true, true);