INSERT INTO warehouse (title, city, street, latitude, longitude, is_active) VALUES
('General warehouse', 'Kyiv', '54 Dovzhenka str.', 40.127, 12.128, true),
('West wholesale warehouse', 'Ivano-Frankivsk', '129 Eugena Konovaltsya str.', 8.12, 7.492, true),
('Regional IF retail warehouse', 'Ivano-Frankivsk', '22 Vasylianok str.', 38.019, 7.433, true),
('Regional LV retail warehouse', 'Lviv', '48 Prokopenka str.', 33.588, 14.007, true),
('Regional TP retail warehouse', 'Ternopil', '2 Panasa Myrnogo str.', 25.304, 9.871, false);

INSERT INTO transporter (name, car_model, load_capacity, is_active) VALUES
('Melnyk O.S.', 'Renault Traffic', 4200, true),
('Mitsui O.S.K. Lines', 'Mercedes Actros', 8800, false),
('Kerry Logistics', 'Volvo FH16', 14200, true),
('TFI International', 'Citroen Jumper', 2600, true),
('Kintetsu World Express', 'Volkswagen Crafter', 5550, true),
('Landstar System', 'DAF LF', 11000, true),
('Emirates Group', 'Mercedes Sprinter', 4820, true),
('CSX Corp.', 'Freightliner FLD 120 HD', 22400, true),
('Norfolk Southern', 'Peugeot Expert', 2500, true),
('Freight Inc.', 'Iveco Strator', 12400, true),
('CJ Logistics', 'Mercedes-Benz Atego 815', 7350, true),
('Yamato Holdings', 'Renault Midlum 300 DXi', 6200, true),
('Bollore Logistics', 'Scania P230', 7600, true),
('Wan Hai Lines', 'Volvo FL 240', 6920, true),
('Dachser Group', 'Man TGL', 10400, true);

INSERT INTO delivery (warehouse_from, warehouse_to, transporter_id, cargo_name, cargo_amount, status) VALUES
(1, 2, 1, 'Tomato', 4000, 'CREATED'),
(1, 3, 4, 'Cucumber', 2000, 'PROCESSING'),
(2, 4, 2, 'Banana', 13800, 'DELIVERED'),
(3, 4, 5, 'Pineapple', 5280, 'SHIPPING'),
(2, 3, 8, 'Flour', 10000, 'SHIPPING'),
(1, 4, 6, 'Groats', 10600, 'CREATED'),
(3, 5, 3, 'Apple', 4900, 'DECLINED'),
(2, 4, 8, 'Sunflower oil', 10180, 'PROCESSING'),
(2, 5, 7, 'Olive oil', 4280, 'DELIVERED'),
(1, 2, 2, 'Mineral water', 8160, 'CREATED'),
(4, 3, 1, 'Sweets', 4120, 'SHIPPING'),
(1, 3, 5, 'Fish', 5300, 'DELIVERED');