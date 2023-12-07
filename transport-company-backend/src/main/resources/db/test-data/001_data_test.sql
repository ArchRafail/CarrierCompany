INSERT INTO users (email, password, first_name, last_name, is_disabled, role) VALUES
('admin@gmail.com', '$2a$10$Ybm8TtAnAOh612FLxy8DO.aiqor7l5aZVjhUwwB/cQBc.HFIBs6xi', 'Jeth', 'Ellison', false, 'ADMIN'),
('manager@gmail.com', '$2a$10$Ybm8TtAnAOh612FLxy8DO.aiqor7l5aZVjhUwwB/cQBc.HFIBs6xi', 'Eugine', 'Bryde', false, 'MANAGER'),
('manager2@gmail.com', '$2a$10$Ybm8TtAnAOh612FLxy8DO.aiqor7l5aZVjhUwwB/cQBc.HFIBs6xi', 'Foster', 'Ryan', false, 'MANAGER'),
('user@gmail.com', '$2a$10$Ybm8TtAnAOh612FLxy8DO.aiqor7l5aZVjhUwwB/cQBc.HFIBs6xi', 'Mike', 'Vincent', false, 'USER'),
('disabled@gmail.com', '$2a$10$Ybm8TtAnAOh612FLxy8DO.aiqor7l5aZVjhUwwB/cQBc.HFIBs6xi', 'Arin', 'Pinnington', true, 'USER'),
('user2@gmail.com', '$2a$10$Ybm8TtAnAOh612FLxy8DO.aiqor7l5aZVjhUwwB/cQBc.HFIBs6xi', 'Andrew', 'Colorod', false, 'USER');

INSERT INTO warehouse (title, city, street, latitude, longitude, is_active) VALUES
('General warehouse', 'Kyiv', '54 Dovzhenka str.', 40.127, 12.128, true),
('West wholesale warehouse', 'Ivano-Frankivsk', '129 Eugena Konovaltsya str.', 8.12, 7.492, true),
('Regional IF retail warehouse', 'Ivano-Frankivsk', '22 Vasylianok str.', 38.019, 7.433, true),
('Regional LV retail warehouse', 'Lviv', '48 Prokopenka str.', 33.588, 14.007, true),
('Regional TP retail warehouse', 'Ternopil', '2 Panasa Myrnogo str.', 25.304, 9.871, false);

INSERT INTO transporter (name, car_model, load_capacity, is_active) VALUES
('Melnyk O.S.', 'Renault Traffic', 4200, true),
('Mitsui O.S.K. Lines', 'Mercedes Actros', 8800, true),
('Kerry Logistics', 'Volvo FH16', 14200, true),
('TFI International', 'Citroen Jumper', 2600, true),
('Kintetsu World Express', 'Volkswagen Crafter', 5550, true),
('Landstar System', 'DAF LF', 11000, true),
('Emirates Group', 'Mercedes Sprinter', 4820, false),
('CSX Corp.', 'Freightliner FLD 120 HD', 22400, true),
('Norfolk Southern', 'Peugeot Expert', 2500, true),
('Freight Inc.', 'Iveco Strator', 12400, true),
('CJ Logistics', 'Mercedes-Benz Atego 815', 7350, true),
('Yamato Holdings', 'Renault Midlum 300 DXi', 6200, true),
('Bollore Logistics', 'Scania P230', 7600, true),
('Wan Hai Lines', 'Volvo FL 240', 6920, true),
('Dachser Group', 'Man TGL', 10400, true);

INSERT INTO delivery (warehouse_from, warehouse_to, transporter_id, cargo_name, cargo_amount, created, scheduled, actual, status) VALUES
(1, 2, 10, 'Sugar', 11960, '2023-08-22T09:29:00', '2023-10-02T23:59:59', '2023-10-05T11:41:15', 'DELIVERED'),
(2, 4, 2, 'Banana', 13800, '2023-09-22T11:22:00', '2023-11-02T23:59:59', '2023-11-01T16:32:03', 'DELIVERED'),
(3, 4, 5, 'Pineapple', 5280, '2023-09-27T10:44:00', '2023-11-29T23:59:59', null, 'SHIPPING'),
(1, 2, 1, 'Tomato', 4000, '2023-10-02T13:15:00', '2023-11-22T23:59:59', null, 'CREATED'),
(1, 3, 4, 'Cucumber', 2000, '2023-10-04T09:11:00', '2023-11-24T23:59:59', null, 'PROCESSING'),
(2, 5, 7, 'Olive oil', 4280, '2023-10-10T12:12:00', '2023-11-04T23:59:59', '2023-11-04T15:08:09', 'DELIVERED'),
(2, 3, 8, 'Flour', 10000, '2023-10-17T14:35:00', '2023-11-27T23:59:59', null, 'SHIPPING'),
(1, 4, 6, 'Groats', 10600, '2023-10-18T16:53:00', '2023-12-15T23:59:59', null, 'CREATED'),
(3, 5, 3, 'Apple', 4900, '2023-10-20T11:36:00', '2023-12-08T23:59:59', null, 'DECLINED'),
(1, 3, 5, 'Fish', 5300, '2023-10-25T09:41:00', '2023-11-06T23:59:59', '2023-11-07T10:16:42', 'DELIVERED'),
(2, 4, 8, 'Sunflower oil', 10180, '2023-10-29T14:38:00', '2023-12-12T23:59:59', null, 'PROCESSING'),
(1, 2, 2, 'Mineral water', 8160, '2023-11-02T11:09:00', '2023-12-21T23:59:59', null, 'CREATED'),
(4, 3, 1, 'Sweets', 4120, '2023-11-06T10:26:00', '2023-12-20T23:59:59', null, 'SHIPPING');