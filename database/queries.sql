USE db_veterinary_clinic;
GO

-- 1. Ver todas las citas de una mascota
DECLARE @pet_id INT = 1; --id mascota a buscar

SELECT p.pet_id, p.name AS pet_name, p.species,
    ap.appointment_date, ap.reason, ap.status,
    u.name AS vet_name
FROM pet p
INNER JOIN appointment ap
    ON ap.pet_id = p.pet_id
INNER JOIN app_user u
    ON u.user_id = ap.veterinarian_id
WHERE p.pet_id = @pet_id
ORDER BY ap.appointment_date DESC;
GO

-- 2. Mostrar últimos 3 tratamientos de una mascota (por cita)
DECLARE @pet_id INT = 2;

SELECT TOP 3
    mr.medical_record_id,
    mr.record_date,
    ap.appointment_id,
    t.name AS treatment_name,
    mrt.dosage,
    mrt.treatment_duration,
    u.name AS vet_name
FROM medical_record mr
INNER JOIN medical_record_treatment mrt
    ON mrt.medical_record_id = mr.medical_record_id
INNER JOIN treatment t
    ON t.treatment_id = mrt.treatment_id
INNER JOIN app_user u
    ON u.user_id = mr.veterinarian_id
LEFT JOIN appointment ap
    ON ap.appointment_id = mr.appointment_id
WHERE mr.pet_id = @pet_id
ORDER BY mr.record_date DESC;
GO

-- 3. Mostrar servicios realizados a una mascota
DECLARE @pet_id INT = 2;

SELECT p.pet_id, p.name AS pet_name,
    ap.appointment_id, ap.appointment_date,
    s.name AS service_name, s.duration_minutes
FROM pet p
INNER JOIN appointment ap
    ON ap.pet_id = p.pet_id
INNER JOIN appointment_service aps
    ON aps.appointment_id = ap.appointment_id
INNER JOIN service s
    ON s.service_id = aps.service_id
WHERE p.pet_id = @pet_id
    AND ap.appointment_type = 'Service'
ORDER BY ap.appointment_date DESC;
GO

-- 4. Mostrar historial médico completo de una mascota
DECLARE @pet_id INT = 1;

SELECT p.pet_id, p.name AS pet_name,
    mr.medical_record_id, mr.record_date, mr.diagnosis, mr.notes, mr.recorded_weight,
    u.name AS vet_name,
    t.name AS treatment_name, mrt.dosage, mrt.treatment_duration
FROM pet p
INNER JOIN medical_record mr
    ON mr.pet_id = p.pet_id
INNER JOIN app_user u
    ON u.user_id = mr.veterinarian_id
LEFT JOIN medical_record_treatment mrt
    ON mrt.medical_record_id = mr.medical_record_id
LEFT JOIN treatment t
    ON t.treatment_id = mrt.treatment_id
WHERE p.pet_id = @pet_id
ORDER BY mr.record_date DESC;
GO

-- 5. Mostrar mascotas sin vacunas
SELECT p.pet_id, p.name AS pet_name, p.species, o.name AS owner_name
FROM pet p
INNER JOIN app_user o
    ON o.user_id = p.owner_id
WHERE p.deleted_at IS NULL
    AND p.pet_id NOT IN (
        SELECT vr.pet_id
        FROM vaccination_record vr
    )
ORDER BY p.name;
GO

-- 6. Mostrar todas las vacunas que se han aplicado a una mascota
DECLARE @pet_id INT = 1;

SELECT p.pet_id, p.name AS pet_name,
    v.name AS vaccine_name, v.prevented_disease,
    vr.application_date, vr.next_dose_date,
    u.name AS vet_name
FROM pet p
INNER JOIN vaccination_record vr
    ON vr.pet_id = p.pet_id
INNER JOIN vaccine v
    ON v.vaccine_id = vr.vaccine_id
INNER JOIN app_user u
    ON u.user_id = vr.veterinarian_id
WHERE p.pet_id = @pet_id
ORDER BY vr.application_date DESC;
GO

-- 7. Mostrar mascotas esterilizadas y no esterilizadas
-- 7a. Conteo por estado de esterilización
SELECT 
    is_neutered AS sterilization_status, 
    COUNT(*) AS total_pets
FROM pet
WHERE deleted_at IS NULL
GROUP BY is_neutered;

-- 7b. Detalle de cada mascota con su estado
SELECT pet_id, name AS pet_name, species, 'Esterilizado' AS sterilization_status
FROM pet
WHERE deleted_at IS NULL AND is_neutered = 1

UNION ALL

SELECT pet_id, name AS pet_name, species, 'No esterilizado' AS sterilization_status
FROM pet
WHERE deleted_at IS NULL AND is_neutered = 0
ORDER BY sterilization_status DESC, pet_name;
GO

-- 8. Mostrar mascotas con mayor número de citas
SELECT p.pet_id, p.name AS pet_name, COUNT(ap.appointment_id) AS total_appointments
FROM pet p
INNER JOIN appointment ap
    ON ap.pet_id = p.pet_id
GROUP BY p.pet_id, p.name
HAVING COUNT(ap.appointment_id) = (
    SELECT MAX(cnt)
    FROM (
        SELECT COUNT(*) AS cnt
        FROM appointment
        GROUP BY pet_id
    ) AS counts_per_pet
)
ORDER BY total_appointments DESC;
GO

-- 9. Promedio de citas por veterinario
-- 9a. Promedio general de citas por veterinario
SELECT AVG(cnt * 1.0) AS avg_appointments_per_vet
FROM (
    SELECT COUNT(*) AS cnt
    FROM appointment
    GROUP BY veterinarian_id
) AS counts_per_vet;
GO

-- 9b. Veterinarios por encima del promedio de citas
SELECT u.user_id, u.name AS vet_name, COUNT(ap.appointment_id) AS total_appointments
FROM app_user u
INNER JOIN appointment ap
    ON ap.veterinarian_id = u.user_id
GROUP BY u.user_id, u.name
HAVING COUNT(ap.appointment_id) > (
    SELECT AVG(cnt * 1.0)
    FROM (
        SELECT COUNT(*) AS cnt
        FROM appointment
        GROUP BY veterinarian_id
    ) AS counts_per_vet
)
ORDER BY total_appointments DESC;
GO

-- 10. Citas realizadas por cada propietario
SELECT o.user_id AS owner_id, o.name AS owner_name,
    COUNT(ap.appointment_id) AS total_appointments
FROM app_user o
INNER JOIN pet p
    ON p.owner_id = o.user_id
INNER JOIN appointment ap
    ON ap.pet_id = p.pet_id
GROUP BY o.user_id, o.name
ORDER BY total_appointments DESC;
GO

-- 11. Peso mínimo, máximo y promedio registrado por mascota
SELECT p.pet_id, p.name AS pet_name,
    MIN(mr.recorded_weight) AS min_weight,
    MAX(mr.recorded_weight) AS max_weight,
    AVG(mr.recorded_weight) AS avg_weight,
    COUNT(mr.medical_record_id) AS total_records
FROM pet p
INNER JOIN medical_record mr
    ON mr.pet_id = p.pet_id
WHERE mr.recorded_weight IS NOT NULL
GROUP BY p.pet_id, p.name
HAVING COUNT(mr.medical_record_id) > 1
ORDER BY p.name;
GO

-- extra:
-- 12. Mascotas con vacunas próximas a vencer (próximos 30 días)
SELECT p.pet_id, p.name AS pet_name, o.name AS owner_name,
    v.name AS vaccine_name, vr.next_dose_date
FROM vaccination_record vr
INNER JOIN pet p
    ON p.pet_id = vr.pet_id
INNER JOIN app_user o
    ON o.user_id = p.owner_id
INNER JOIN vaccine v
    ON v.vaccine_id = vr.vaccine_id
WHERE vr.next_dose_date BETWEEN CAST(SYSUTCDATETIME() AS DATE) AND DATEADD(DAY, 30, CAST(SYSUTCDATETIME() AS DATE))
ORDER BY vr.next_dose_date ASC;
GO