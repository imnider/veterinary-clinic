USE db_veterinary_clinic;
GO

-- =========================================================
-- INSERTS: role
-- =========================================================
INSERT INTO role (name, description) VALUES
('CLIENTE', 'Dueño de una o mas mascotas registradas en la clinica'),
('VETERINARIO', 'Personal medico encargado de atender mascotas'),
('ADMIN', 'Personal encargado de la gestion general del sistema');
GO

-- =========================================================
-- INSERTS: service
-- =========================================================
INSERT INTO service (name, description, duration_minutes) VALUES
('Baño', 'Baño e higiene general de la mascota', 45),
('Peluqueria', 'Corte y arreglo de pelaje', 60),
('Corte de uñas', 'Corte y limado de uñas', 15),
('Limpieza de oidos', 'Limpieza e higiene de oidos', 15),
('Desparasitacion externa', 'Aplicacion de producto antipulgas/antigarrapatas', 20);
GO

-- =========================================================
-- INSERTS: treatment
-- =========================================================
INSERT INTO treatment (name, description, treatment_type) VALUES
('Antibiotico oral', 'Tratamiento antibiotico via oral', 'Medicamento'),
('Antiinflamatorio', 'Tratamiento para reducir inflamacion y dolor', 'Medicamento'),
('Sutura', 'Cierre de heridas mediante puntos', 'Procedimiento'),
('Desparasitacion interna', 'Tratamiento antiparasitario via oral', 'Medicamento'),
('Fluidoterapia', 'Administracion de sueros intravenosos', 'Procedimiento'),
('Radiografia', 'Estudio de imagen diagnostica', 'Procedimiento');
GO

-- =========================================================
-- INSERTS: vaccine
-- =========================================================
INSERT INTO vaccine (name, prevented_disease) VALUES
('Rabia', 'Rabia'),
('Sextuple (DHPPi + L)', 'Moquillo, Hepatitis, Parvovirus, Parainfluenza, Leptospirosis'),
('Triple felina', 'Panleucopenia, Rinotraqueitis, Calicivirus'),
('Leucemia felina', 'Leucemia viral felina'),
('Bordetella', 'Tos de las perreras');
GO

USE db_veterinary_clinic;
GO

-- =========================================================
-- INSERTS: app_user
-- user_id 1      -> ADMIN
-- user_id 2,3,4  -> VETERINARIO
-- user_id 5..10  -> CLIENTE
-- =========================================================
INSERT INTO app_user (name, username, email, phone, password_hash, specialty, address) VALUES
('Maria Torres',  'mtorres',  'maria.torres@vetclinic.com',  '0991234567', '$2b$10$adminHash0000000000000000000000000001', NULL,               'Av. Principal 123, Guayaquil'),
('Carlos Vera',   'cvera',    'carlos.vera@vetclinic.com',   '0991234568', '$2b$10$vetHash00000000000000000000000000002', 'Medicina general', 'Calle 10 #45, Guayaquil'),
('Ana Salas',     'asalas',   'ana.salas@vetclinic.com',     '0991234569', '$2b$10$vetHash00000000000000000000000000003', 'Cirugia',           'Av. del Bombero 200, Guayaquil'),
('Pedro Nunez',   'pnunez',   'pedro.nunez@vetclinic.com',   '0991234570', '$2b$10$vetHash00000000000000000000000000004', 'Dermatologia',      'Cdla. Kennedy Norte, Guayaquil'),
('Laura Gomez',   'lgomez',   'laura.gomez@gmail.com',       '0981112233', '$2b$10$cliHash00000000000000000000000000005', NULL,               'Urdesa Central, Guayaquil'),
('Jose Ramirez',  'jramirez', 'jose.ramirez@gmail.com',      '0981112234', '$2b$10$cliHash00000000000000000000000000006', NULL,               'Sauces 4, Guayaquil'),
('Sofia Diaz',    'sdiaz',    'sofia.diaz@gmail.com',        '0981112235', '$2b$10$cliHash00000000000000000000000000007', NULL,               'Alborada 6ta Etapa, Guayaquil'),
('Miguel Angel',  'mangel',   'miguel.angel@gmail.com',      '0981112236', '$2b$10$cliHash00000000000000000000000000008', NULL,               'Via Samborondon Km 2, Guayaquil'),
('Valentina Cruz','vcruz',    'valentina.cruz@gmail.com',    '0981112237', '$2b$10$cliHash00000000000000000000000000009', NULL,               'Ceibos Norte, Guayaquil'),
('Diego Fernandez','dfernandez','diego.fernandez@gmail.com', '0981112238', '$2b$10$cliHash00000000000000000000000000010', NULL,               'Puerto Azul, Guayaquil');
GO

-- =========================================================
-- INSERTS: user_role
-- =========================================================
INSERT INTO user_role (user_id, role_id) VALUES
(1, 3),  -- Maria Torres      -> ADMIN
(2, 2),  -- Carlos Vera       -> VETERINARIO
(3, 2),  -- Ana Salas         -> VETERINARIO
(4, 2),  -- Pedro Nunez       -> VETERINARIO
(5, 1),  -- Laura Gomez       -> CLIENTE
(6, 1),  -- Jose Ramirez      -> CLIENTE
(7, 1),  -- Sofia Diaz        -> CLIENTE
(8, 1),  -- Miguel Angel      -> CLIENTE
(9, 1),  -- Valentina Cruz    -> CLIENTE
(10, 1); -- Diego Fernandez   -> CLIENTE
GO

-- =========================================================
-- INSERTS: pet
-- owner_id 5..10 -> clientes registrados en app_user
-- =========================================================
INSERT INTO pet (owner_id, name, species, breed, birth_date, sex, weight, is_neutered) VALUES
(5,  'Max',     'Perro', 'Labrador Retriever',   '2021-03-15', 'M', 28.50, 1),
(5,  'Luna',    'Gato',  'Comun europeo',        '2022-07-20', 'F', 4.20,  1),
(6,  'Rocky',   'Perro', 'Pastor Aleman',        '2020-11-05', 'M', 32.00, 0),
(6,  'Michi',   'Gato',  'Siames',               '2023-01-10', 'F', 3.80,  0),
(7,  'Toby',    'Perro', 'Beagle',               '2022-05-22', 'M', 12.30, 1),
(7,  'Nina',    'Gato',  'Persa',                '2021-09-14', 'F', 4.50,  1),
(8,  'Simba',   'Gato',  'Maine Coon',           '2020-02-28', 'M', 6.10,  0),
(9,  'Coco',    'Perro', 'Poodle',               '2023-04-03', 'F', 5.60,  0),
(9,  'Bruno',   'Perro', 'Bulldog Frances',      '2021-12-19', 'M', 11.80, 1),
(10, 'Mia',     'Gato',  'Comun europeo',        '2022-10-08', 'F', 4.00,  1);
GO

-- =========================================================
-- INSERTS: appointment
-- appointment_id 1,3,5,7,9,11,12  -> MEDICAL
-- appointment_id 2,4,6,8,10       -> SERVICE
-- pet_id 1..10 corresponden a las mascotas insertadas arriba
-- =========================================================
INSERT INTO appointment (pet_id, veterinarian_id, appointment_date, reason, appointment_type, status) VALUES
(1,  2, '2026-01-10 09:00', 'Chequeo general por decaimiento',        'MEDICAL', 'COMPLETED'),
(2,  2, '2026-01-15 10:30', 'Baño y peluqueria',                      'SERVICE', 'COMPLETED'),
(3,  3, '2026-02-01 11:00', 'Herida en pata trasera',                 'MEDICAL', 'COMPLETED'),
(4,  3, '2026-02-05 15:00', 'Corte de uñas y limpieza de oidos',      'SERVICE', 'COMPLETED'),
(5,  4, '2026-02-10 08:30', 'Alergia cutanea',                        'MEDICAL', 'SCHEDULED'),
(6,  4, '2026-02-12 13:00', 'Desparasitacion externa',                'SERVICE', 'SCHEDULED'),
(7,  2, '2026-03-01 09:30', 'Vomito y diarrea',                       'MEDICAL', 'COMPLETED'),
(8,  3, '2026-03-05 16:00', 'Baño medicado',                          'SERVICE', 'COMPLETED'),
(9,  4, '2026-03-10 10:00', 'Control post operatorio',                'MEDICAL', 'CANCELLED'),
(10, 2, '2026-03-15 12:00', 'Peluqueria y corte de uñas',             'SERVICE', 'SCHEDULED'),
(1,  3, '2026-04-02 09:00', 'Fractura leve en pata delantera',        'MEDICAL', 'COMPLETED'),
(5,  4, '2026-04-08 14:00', 'Revision por dermatitis recurrente',     'MEDICAL', 'SCHEDULED');
GO

-- =========================================================
-- INSERTS: appointment_service
-- Vinculadas a los appointment_id de tipo SERVICE (2,4,6,8,10)
-- =========================================================
INSERT INTO appointment_service (appointment_id, service_id) VALUES
(2, 1),   -- Baño
(2, 2),   -- Peluqueria
(4, 3),   -- Corte de uñas
(4, 4),   -- Limpieza de oidos
(6, 5),   -- Desparasitacion externa
(8, 1),   -- Baño medicado -> Baño
(8, 4),   -- Limpieza de oidos
(10, 2),  -- Peluqueria
(10, 3),  -- Corte de uñas
(10, 5);  -- Desparasitacion externa
GO

-- =========================================================
-- INSERTS: medical_record
-- Vinculadas a los appointment_id de tipo MEDICAL
-- (2, 5, 9 CANCELLED se excluye; ver appointment 9 = CANCELLED)
-- =========================================================
INSERT INTO medical_record (pet_id, appointment_id, veterinarian_id, diagnosis, notes, recorded_weight) VALUES
(1, 1,  2, 'Gastroenteritis leve',                 'Se recomienda dieta blanda por 3 dias',            8.20),
(3, 3,  3, 'Herida superficial en pata trasera',   'Se realizo limpieza y sutura',                     15.50),
(5, 5,  4, 'Dermatitis alergica',                  'Se indica evitar contacto con cesped',             6.80),
(7, 7,  2, 'Gastroenteritis moderada',             'Se administra fluidoterapia',                      10.30),
(1, 11, 3, 'Fractura leve en radio',               'Inmovilizacion y control en 15 dias',              8.40),
(5, 12, 4, 'Dermatitis recurrente',                'Se ajusta tratamiento topico',                     7.00);
GO

-- =========================================================
-- INSERTS: medical_record_treatment
-- =========================================================
INSERT INTO medical_record_treatment (medical_record_id, treatment_id, dosage, treatment_duration) VALUES
(1, 1, '5 ml cada 12 horas',      '7 dias'),
(1, 4, '1 tableta cada 24 horas', '3 dias'),
(2, 3, 'Punto unico',             'Retiro en 10 dias'),
(2, 2, '1 tableta cada 12 horas', '5 dias'),
(3, 2, '1 tableta cada 24 horas', '10 dias'),
(4, 5, '250 ml en una sesion',    '1 dia'),
(4, 1, '5 ml cada 12 horas',      '5 dias'),
(5, 6, 'Estudio unico',           '1 dia'),
(5, 2, '1 tableta cada 24 horas', '7 dias'),
(6, 2, '1 tableta cada 12 horas', '10 dias');
GO

-- =========================================================
-- INSERTS: vaccination_record
-- =========================================================
INSERT INTO vaccination_record (pet_id, vaccine_id, veterinarian_id, application_date, next_dose_date) VALUES
(1,  1, 2, '2025-06-01', '2026-06-01'),
(2,  2, 3, '2025-06-05', '2025-09-05'),
(3,  1, 4, '2025-06-10', '2026-06-10'),
(4,  3, 2, '2025-06-15', '2025-12-15'),
(5,  4, 3, '2025-07-01', '2026-01-01'),
(6,  5, 4, '2025-07-05', '2025-10-05'),
(7,  2, 2, '2025-07-10', '2025-10-10'),
(8,  1, 3, '2025-07-15', '2026-07-15'),
(9,  3, 4, '2025-08-01', '2026-02-01'),
(10, 5, 2, '2025-08-10', '2025-11-10');
GO