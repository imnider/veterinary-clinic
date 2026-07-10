package com.ug.veterinary.veterinary_clinic.constants;

public final class ValidationConstants {

    private ValidationConstants() {}

    // ── Tamaños: user ──────────────────────────────────────────────────
    public static final int EMAIL_MAX = 150;
    public static final int PASSWORD_MIN = 6;
    public static final int USERNAME_MAX = 100;
    public static final int USER_NAME_MAX = 150;

    // ── Tamaños: pet ─────────────────────────────────────────────
    public static final int PET_NAME_MAX = 100;
    public static final int PET_SPECIES_MAX = 50;
    public static final int PET_BREED_MAX = 50;

    // ── Tamaños: appointment ─────────────────────────────────────
    public static final int APPOINTMENT_REASON_MAX = 255;

    // ── Tamaños: medical record ─────────────────────────────────────
    public static final int MEDICAL_RECORD_DIAGNOSIS_MAX = 500;
    public static final int MEDICAL_RECORD_NOTES_MAX = 1000;

    // ── Mensajes: roles ───────────────────────────────────────────
    public static final String ROLES_REQUIRED = "Debe asignar al menos un rol";

    // ── Mensajes: pet ────────────────────────────────────────────
    public static final String PET_NAME_REQUIRED = "El nombre de la mascota es obligatorio";
    public static final String PET_NAME_MAX_MESSAGE = "El nombre no puede superar los " + PET_NAME_MAX + " caracteres";
    public static final String PET_SPECIES_REQUIRED = "La especie es obligatoria";
    public static final String PET_SPECIES_MAX_MESSAGE = "La especie no puede superar los " + PET_SPECIES_MAX + " caracteres";
    public static final String PET_BREED_MAX_MESSAGE = "La raza no puede superar los " + PET_BREED_MAX + " caracteres";
    public static final String PET_SEX_REQUIRED = "El sexo de la mascota es obligatorio";
    public static final String PET_BIRTH_DATE_INVALID = "La fecha de nacimiento no puede ser futura";
    public static final String PET_WEIGHT_INVALID = "El peso debe ser un valor positivo";

    // ── Mensajes: user ───────────────────────────────────────
    public static final String USERNAME_REQUIRED = "El nombre de usuario es obligatorio";
    public static final String USERNAME_MAX_MESSAGE = "El nombre de usuario no puede superar los " + USERNAME_MAX + " caracteres";
    public static final String PASSWORD_REQUIRED = "La contraseña es obligatoria";
    public static final String PASSWORD_MIN_MESSAGE = "La contraseña debe tener al menos " + PASSWORD_MIN + " caracteres";
    public static final String EMAIL_REQUIRED = "El correo es obligatorio";
    public static final String EMAIL_FORMAT = "El correo no tiene un formato valido";
    public static final String EMAIL_MAX_MESSAGE = "El correo no puede superar los " + EMAIL_MAX + " caracteres";
    public static final String USER_NAME_REQUIRED = "El nombre es obligatorio";
    public static final String USER_NAME_MAX_MESSAGE = "El nombre no puede superar los " + USER_NAME_MAX + " caracteres";

        // ── Mensajes: appointment ────────────────────────────────────
    public static final String APPOINTMENT_PET_ID_REQUIRED = "Debe especificar la mascota";
    public static final String APPOINTMENT_VETERINARIAN_ID_REQUIRED = "Debe especificar el veterinario";
    public static final String APPOINTMENT_DATE_REQUIRED = "Debe especificar la fecha de la cita";
    public static final String APPOINTMENT_DATE_FUTURE = "La fecha de la cita debe ser futura";
    public static final String APPOINTMENT_TYPE_REQUIRED = "Debe especificar el tipo de cita";
    public static final String APPOINTMENT_TYPE_INVALID = "El tipo de cita debe ser Medical o Service";
    public static final String APPOINTMENT_REASON_MAX_MESSAGE = "El motivo no puede superar los " + APPOINTMENT_REASON_MAX + " caracteres";

    // ── Mensajes: medical record ────────────────────────────────────
    public static final String MEDICAL_RECORD_APPOINTMENT_REQUIRED = "La cita es obligatoria";
    public static final String MEDICAL_RECORD_DIAGNOSIS_REQUIRED = "El diagnóstico es obligatorio";
    public static final String MEDICAL_RECORD_DIAGNOSIS_MAX_MESSAGE = "El diagnóstico no puede superar los " + MEDICAL_RECORD_DIAGNOSIS_MAX + " caracteres";
    public static final String MEDICAL_RECORD_NOTES_MAX_MESSAGE = "Las notas no pueden superar los " + MEDICAL_RECORD_NOTES_MAX + " caracteres";
    public static final String MEDICAL_RECORD_WEIGHT_INVALID = "El peso registrado debe ser un valor positivo";

}