package modelo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.Part;

public class ImagenUtils {
    
    // Guarda una imagen en la ruta especificada con el nombre dado
    public static boolean guardarImagen(Part imagenPart, String nombreImagen) {
        String rutaDestino = "C:/Users/ismae/Desktop/Biblioteca/web/assets/" + nombreImagen;
        try (InputStream entrada = imagenPart.getInputStream();
             FileOutputStream salida = new FileOutputStream(rutaDestino)) {
            byte[] buffer = new byte[1024];
            int longitud;
            while ((longitud = entrada.read(buffer)) != -1) {
                salida.write(buffer, 0, longitud);
            }
            return true; 
        } catch (IOException e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    // Elimina una imagen según el ISBN proporcionado
    public static boolean eliminarImagen(String isbn) {
        String rutaImagen = "C:/Users/ismae/Desktop/Biblioteca/web/assets/" + "portada_" + isbn + ".jpg";
        File imagenPortada = new File(rutaImagen);
        return imagenPortada.delete();
    }
}
