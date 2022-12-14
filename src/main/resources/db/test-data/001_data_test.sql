INSERT INTO warehouse (title, city, street, latitude, longitude) VALUES
('General warehouse', 'Kyiv', '54 Dovzhenka str.', 40.127, 12.128),
('West wholesale warehouse', 'Ivano-Frankivsk', '129 Eugena Konovaltsya str.', 8.12, 7.492),
('Regional IF retail warehouse', 'Ivano-Frankivsk', '22 Vasylianok str.', 38.019, 7.433),
('Regional LV retail warehouse', 'Lviv', '48 Prokopenka str.', 33.588, 14.007);

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
(1, 2, 1, 'Tomato', 4000, 1),
(1, 3, 4, 'Cucumber', 2000, 2),
(2, 4, 3, 'Banana', 13800, 4),
(3, 4, 5, 'Pineapple', 5280, 3),
(2, 3, 8, 'Flour', 10000, 3),
(1, 4, 6, 'Groats', 10600, 1),
(3, 2, 2, 'Apple', 4900, 5),
(2, 4, 8, 'Sunflower oil', 10180, 2),
(2, 3, 7, 'Olive oil', 4280, 4),
(1, 2, 2, 'Mineral water', 8160, 1),
(4, 3, 1, 'Sweets', 4120, 3),
(1, 3, 5, 'Fish', 5300, 4);