package com.ug.veterinary.veterinary_clinic.constants;

public final class MessageConstants {

    private MessageConstants() {}

    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String VALIDATION_ERROR = "Error de validacion";
    public static final String INVALID_CREDENTIALS = "Usuario o contraseña incorrectos";
    public static final String ACCESS_DENIED = "No tienes permisos para realizar esta accion";
    public static final String INTERNAL_SERVER_ERROR = "Ha ocurrido un error inesperado";

    public static final String IMAGE_UPLOAD_ERROR = "No se pudo subir la imagen, intenta nuevamente";
    public static final String IMAGE_DELETE_ERROR = "No se pudo eliminar la imagen anterior";

    public static final String PET_OWNER_NOT_FOUND = "El propietario especificado no existe";
    public static final String PET_OWNER_INVALID_ROLE = "El usuario especificado no tiene rol de cliente";
    public static final String PET_OWNER_ID_REQUIRED = "Debes especificar el propietario de la mascota";
}
