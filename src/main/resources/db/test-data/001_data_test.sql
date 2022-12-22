INSERT INTO address (city, street, latitude, longitude) VALUES
('Kyiv', '54 Dovzhenka str.', 40.127, 12.128),
('Ivano-Frankivsk', '129 Eugena Konovaltsya str.', 8.12, 7.492),
('Ivano-Frankivsk', '22 Vasylianok str.', 38.019, 7.433),
('Lviv', '48 Prokopenka str.', 33.588, 14.007);

INSERT INTO warehouse (title, address_id) VALUES
('General warehouse', 1),
('West wholesale warehouse', 2),
('Regional IF retail warehouse', 3),
('Regional LV retail warehouse', 4);

INSERT INTO transporter (name, car_model, load_capacity) VALUES
('Melnyk O.S.', 'Renault Traffic', 4200),
('Carrier experts', 'Mercedes Actros', 8800),
('Global delivery', 'Volvo FH16', 14200),
('Quick currier', 'Citroen Jumper', 2600),
('Fiesta show', 'Volkswagen Crafter', 5550),
('Nickolas', 'DAF LF', 11000),
('Robert Space', 'Mercedes Sprinter', 4820),
('Delivery Group', 'Man TGL', 10400);

INSERT INTO delivery (warehouse_from, warehouse_to, transporter_id, cargo_name, cargo_amount, status) VALUES
(1, 2, 1, 'Tomato', 4000, 0),
(1, 3, 4, 'Cucumber', 2000, 1),
(2, 4, 3, 'Banana', 13800, 3),
(3, 4, 5, 'Pineapple', 5280, 2),
(2, 3, 8, 'Flour', 10000, 2),
(1, 4, 6, 'Groats', 10600, 0),
(3, 2, 2, 'Apple', 4900, 4),
(2, 4, 8, 'Sunflower oil', 10180, 1),
(2, 3, 7, 'Olive oil', 4280, 3),
(1, 2, 2, 'Mineral water', 8160, 0),
(4, 3, 1, 'Sweets', 4120, 2),
(1, 3, 5, 'Fish', 5300, 3);