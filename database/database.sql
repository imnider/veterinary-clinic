IF DB_ID('db_veterinary_clinic') IS NOT NULL
BEGIN
    ALTER DATABASE db_veterinary_clinic SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE db_veterinary_clinic;
END
GO

CREATE DATABASE db_veterinary_clinic;
GO

USE db_veterinary_clinic;
GO

-- =========================================================
-- TABLE: role
-- =========================================================
CREATE TABLE role (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(50) NOT NULL UNIQUE,
    description NVARCHAR(255) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    deleted_at DATETIME2 NULL
);
GO

-- =========================================================
-- TABLE: app_user
-- =========================================================
CREATE TABLE app_user (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(150) NOT NULL,
    username NVARCHAR(100) NOT NULL UNIQUE,
    email NVARCHAR(150) NOT NULL UNIQUE,
    phone NVARCHAR(20) NULL,
    password_hash NVARCHAR(255) NOT NULL,
    specialty NVARCHAR(100) NULL,
    address NVARCHAR(200) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    deleted_at DATETIME2 NULL
);
GO

-- =========================================================
-- TABLE: user_role (N:M)
-- =========================================================
CREATE TABLE user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT PK_USER_ROLE PRIMARY KEY (user_id, role_id),
    CONSTRAINT FK_USER_ROLE_USER FOREIGN KEY (user_id) REFERENCES app_user(user_id),
    CONSTRAINT FK_USER_ROLE_ROLE FOREIGN KEY (role_id) REFERENCES role(role_id)
);
GO

-- =========================================================
-- TABLE: pet
-- =========================================================
CREATE TABLE pet (
    pet_id INT IDENTITY(1,1) PRIMARY KEY,
    owner_id INT NOT NULL,
    name NVARCHAR(100) NOT NULL,
    photo_url NVARCHAR(500) NULL,
    photo_public_id NVARCHAR(255) NULL,
    species NVARCHAR(50) NOT NULL,
    breed NVARCHAR(50) NULL, --raza
    birth_date DATE NULL,
    sex CHAR(1) NULL, --M/F
    weight DECIMAL(5,2) NULL,
    is_neutered BIT NOT NULL DEFAULT 0, --castrado
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    deleted_at DATETIME2 NULL,
    CONSTRAINT FK_PET_OWNER FOREIGN KEY (owner_id) REFERENCES app_user(user_id),
    CONSTRAINT CK_PET_SEX CHECK (sex IN ('M', 'F'))
);
GO

-- =========================================================
-- TABLE: service (catalog)
-- =========================================================
CREATE TABLE service (
    service_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255) NULL,
    duration_minutes INT NOT NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    deleted_at DATETIME2 NULL
);
GO

-- =========================================================
-- TABLE: appointment
-- =========================================================
CREATE TABLE appointment (
    appointment_id INT IDENTITY(1,1) PRIMARY KEY,
    pet_id INT NOT NULL,
    veterinarian_id INT NOT NULL,
    appointment_date DATETIME2 NOT NULL,
    reason NVARCHAR(255) NULL,
    appointment_type NVARCHAR(20) NOT NULL,
    status NVARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    CONSTRAINT FK_APPOINTMENT_PET FOREIGN KEY (pet_id) REFERENCES pet(pet_id),
    CONSTRAINT FK_APPOINTMENT_USER FOREIGN KEY (veterinarian_id) REFERENCES app_user(user_id),
    CONSTRAINT CK_APPOINTMENT_TYPE CHECK (appointment_type IN ('MEDICAL', 'SERVICE')),
    CONSTRAINT CK_APPOINTMENT_STATUS CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED'))
);
GO

-- =========================================================
-- TABLE: appointment_service (N:M)
-- =========================================================
CREATE TABLE appointment_service (
    appointment_id INT NOT NULL,
    service_id INT NOT NULL,
    CONSTRAINT PK_APPOINTMENT_SERVICE PRIMARY KEY (appointment_id, service_id),
    CONSTRAINT FK_APPOINTMENT_SERVICE_APPOINTMENT FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id),
    CONSTRAINT FK_APPOINTMENT_SERVICE_SERVICE FOREIGN KEY (service_id) REFERENCES service(service_id)
);
GO

-- =========================================================
-- TABLE: treatment (catalog)
-- =========================================================
CREATE TABLE treatment (
    treatment_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255) NULL,
    treatment_type NVARCHAR(50) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    deleted_at DATETIME2 NULL
);
GO

-- =========================================================
-- TABLE: medical_record
-- =========================================================
CREATE TABLE medical_record (
    medical_record_id INT IDENTITY(1,1) PRIMARY KEY,
    pet_id INT NOT NULL,
    appointment_id INT NOT NULL UNIQUE,
    veterinarian_id INT NOT NULL,
    record_date DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    diagnosis NVARCHAR(500) NOT NULL,
    notes NVARCHAR(500) NULL,
    recorded_weight DECIMAL(5,2) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    CONSTRAINT FK_MEDICAL_RECORD_PET FOREIGN KEY (pet_id) REFERENCES pet(pet_id),
    CONSTRAINT FK_MEDICAL_RECORD_APPOINTMENT FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id),
    CONSTRAINT FK_MEDICAL_RECORD_VETERINARIAN FOREIGN KEY (veterinarian_id) REFERENCES app_user(user_id)
);
GO

-- =========================================================
-- TABLE: medical_record_treatment (N:M)
-- =========================================================
CREATE TABLE medical_record_treatment (
    medical_record_id INT NOT NULL,
    treatment_id INT NOT NULL,
    dosage NVARCHAR(100) NULL,
    treatment_duration NVARCHAR(50) NULL,
    CONSTRAINT PK_MEDICAL_RECORD_TREATMENT PRIMARY KEY (medical_record_id, treatment_id),
    CONSTRAINT FK_MEDICAL_RECORD_TREATMENT_RECORD FOREIGN KEY (medical_record_id) REFERENCES medical_record(medical_record_id),
    CONSTRAINT FK_MEDICAL_RECORD_TREATMENT_TREATMENT FOREIGN KEY (treatment_id) REFERENCES treatment(treatment_id)
);
GO

-- =========================================================
-- TABLE: vaccine (catalog)
-- =========================================================
CREATE TABLE vaccine (
    vaccine_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    prevented_disease NVARCHAR(150) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    deleted_at DATETIME2 NULL
);
GO

-- =========================================================
-- TABLE: vaccination_record
-- =========================================================
CREATE TABLE vaccination_record (
    vaccination_record_id INT IDENTITY(1,1) PRIMARY KEY,
    pet_id INT NOT NULL,
    vaccine_id INT NOT NULL,
    veterinarian_id INT NOT NULL,
    application_date DATE NOT NULL,
    next_dose_date DATE NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL,
    CONSTRAINT FK_VACCINATION_RECORD_PET FOREIGN KEY (pet_id) REFERENCES pet(pet_id),
    CONSTRAINT FK_VACCINATION_RECORD_VACCINE FOREIGN KEY (vaccine_id) REFERENCES vaccine(vaccine_id),
    CONSTRAINT FK_VACCINATION_RECORD_VETERINARIAN FOREIGN KEY (veterinarian_id) REFERENCES app_user(user_id)
);
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