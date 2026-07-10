package com.ug.veterinary.veterinary_clinic.constants;

public final class MessageConstants {

    private MessageConstants() {}

    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String VALIDATION_ERROR = "Error de validacion";
    public static final String UNAUTHENTICATED = "No se proporciono un token valido, inicia sesion para continuar";
    public static final String INVALID_CREDENTIALS = "Usuario o contraseña incorrectos";
    public static final String ACCESS_DENIED = "No tienes permisos para realizar esta accion";
    public static final String INTERNAL_SERVER_ERROR = "Ha ocurrido un error inesperado";
    

    public static final String IMAGE_UPLOAD_ERROR = "No se pudo subir la imagen, intenta nuevamente";
    public static final String IMAGE_DELETE_ERROR = "No se pudo eliminar la imagen anterior";

    public static final String PET_OWNER_NOT_FOUND = "El propietario especificado no existe";
    public static final String PET_OWNER_INVALID_ROLE = "El usuario especificado no tiene rol de cliente";
    public static final String PET_OWNER_ID_REQUIRED = "Debes especificar el propietario de la mascota";

    public static final String USERNAME_ALREADY_EXISTS = "El nombre de usuario ya esta en uso";
    public static final String EMAIL_ALREADY_EXISTS = "El correo ya esta registrado";
    public static final String ROLE_NOT_FOUND = "Uno o mas roles especificados no existen";

    public static final String ADMIN_ALREADY_EXISTS = "El usuario administrador ya existe, se omite la creacion";
    public static final String ADMIN_ROLE_NOT_FOUND = "El rol ADMIN no existe en la base de datos. Verifica los inserts del script SQL";
    public static final String ADMIN_CREATED = "Usuario administrador creado exitosamente";

    public static final String PET_NOT_FOUND = "Mascota no encontrada";
    public static final String VETERINARIAN_NOT_FOUND = "Veterinario no encontrado";
    public static final String VETERINARIAN_INVALID_ROLE = "El usuario especificado no tiene rol de veterinario";
    public static final String APPOINTMENT_PET_NOT_OWNED = "La mascota especificada no te pertenece";

    public static final String MEDICAL_RECORD_NOT_FOUND = "No se encontró el historial médico";
    public static final String MEDICAL_RECORD_ALREADY_EXISTS = "La cita ya tiene un historial médico asociado";
    public static final String APPOINTMENT_NOT_FOUND = "Cita no encontrada";
}
