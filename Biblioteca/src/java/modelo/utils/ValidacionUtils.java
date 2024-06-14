package modelo.utils;

public class ValidacionUtils {
    
    // Valida un ISBN según su longitud
    public static boolean validarISBN(String isbn) {
        boolean esValido = true;
        if (isbn == null || isbn.length() != 13) {
            esValido = false;
        }
        return esValido;
    }
}