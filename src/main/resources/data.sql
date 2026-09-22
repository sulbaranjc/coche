-- Script de datos de ejemplo (10 registros) para la tabla 'coche'
-- INSERT IGNORE evita errores de matricula duplicada si la app se reinicia
-- y los datos ya existen (matricula tiene restriccion UNIQUE)

INSERT IGNORE INTO coche (marca, modelo, matricula, anio_fabricacion, color, precio, kilometraje, combustible, transmision) VALUES
('Toyota',     'Corolla',  '1234BCD', 2020, 'Blanco', 18500.00, 32000, 'GASOLINA',  'MANUAL'),
('Volkswagen', 'Golf',     '5678FGH', 2019, 'Gris',   16800.00, 45000, 'DIESEL',    'MANUAL'),
('Tesla',      'Model 3',  '9012JKL', 2022, 'Rojo',   42000.00, 15000, 'ELECTRICO', 'AUTOMATICA'),
('Seat',       'Leon',     '3456MNP', 2018, 'Azul',   13500.00, 60000, 'GASOLINA',  'MANUAL'),
('BMW',        'Serie 3',  '7890QRS', 2021, 'Negro',  35000.00, 22000, 'HIBRIDO',   'AUTOMATICA'),
('Renault',    'Clio',     '2345TUV', 2017, 'Blanco',  9800.00, 78000, 'GASOLINA',  'MANUAL'),
('Audi',       'A4',       '6789WXY', 2020, 'Gris',   28900.00, 34000, 'DIESEL',    'AUTOMATICA'),
('Ford',       'Focus',    '1122ZAB', 2019, 'Rojo',   14200.00, 52000, 'GASOLINA',  'MANUAL'),
('Hyundai',    'Kona',     '3344CDE', 2022, 'Verde',  24500.00,  8000, 'HIBRIDO',   'AUTOMATICA'),
('Peugeot',    '208',      '5566EFG', 2021, 'Blanco', 15900.00, 18000, 'GASOLINA',  'MANUAL');
