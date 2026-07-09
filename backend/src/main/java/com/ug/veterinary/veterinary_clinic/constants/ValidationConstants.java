package com.ug.veterinary.veterinary_clinic.constants;

public final class ValidationConstants {

    private ValidationConstants() {}

    // ── Tamaños: login ──────────────────────────────────────────────────
    public static final int EMAIL_MAX = 150;
    public static final int PASSWORD_MIN = 8;
    public static final int USERNAME_MAX = 100;

    // ── Tamaños: pet ─────────────────────────────────────────────
    public static final int PET_NAME_MAX = 100;
    public static final int PET_SPECIES_MAX = 50;
    public static final int PET_BREED_MAX = 50;

    // ── Mensajes: pet ────────────────────────────────────────────
    public static final String PET_NAME_REQUIRED = "El nombre de la mascota es obligatorio";
    public static final String PET_NAME_MAX_MESSAGE = "El nombre no puede superar los " + PET_NAME_MAX + " caracteres";
    public static final String PET_SPECIES_REQUIRED = "La especie es obligatoria";
    public static final String PET_SPECIES_MAX_MESSAGE = "La especie no puede superar los " + PET_SPECIES_MAX + " caracteres";
    public static final String PET_BREED_MAX_MESSAGE = "La raza no puede superar los " + PET_BREED_MAX + " caracteres";
    public static final String PET_SEX_REQUIRED = "El sexo de la mascota es obligatorio";
    public static final String PET_BIRTH_DATE_INVALID = "La fecha de nacimiento no puede ser futura";
    public static final String PET_WEIGHT_INVALID = "El peso debe ser un valor positivo";

    // ── Mensajes: email ──────────────────────────────────────────
    public static final String EMAIL_REQUIRED = "El correo es obligatorio";
    public static final String EMAIL_FORMAT = "El correo no tiene un formato valido";
    public static final String EMAIL_MAX_MESSAGE = "El correo no puede superar los " + EMAIL_MAX + " caracteres";

    // ── Mensajes: username ───────────────────────────────────────
    public static final String USERNAME_REQUIRED = "El nombre de usuario es obligatorio";
    public static final String USERNAME_MAX_MESSAGE = "El nombre de usuario no puede superar los " + USERNAME_MAX + " caracteres";

    // ── Mensajes: password ───────────────────────────────────────
    public static final String PASSWORD_REQUIRED = "La contraseña es obligatoria";
    public static final String PASSWORD_MIN_MESSAGE = "La contraseña debe tener al menos " + PASSWORD_MIN + " caracteres";
}
