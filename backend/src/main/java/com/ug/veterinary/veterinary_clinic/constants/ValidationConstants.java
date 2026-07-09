package com.ug.veterinary.veterinary_clinic.constants;

public final class ValidationConstants {

    private ValidationConstants() {}

    // ── Tamaños ──────────────────────────────────────────────────
    public static final int EMAIL_MAX = 150;
    public static final int PASSWORD_MIN = 8;
    public static final int USERNAME_MAX = 100;

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
