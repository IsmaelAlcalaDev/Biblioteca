package modelo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FechaUtils {
    
    // Convierte una cadena de texto en formato "yyyy-MM-dd" a una fecha Date en formato "dd-MM-yyyy"
    public static Date convertirAFecha(String fechaString) {
        SimpleDateFormat formatoActual = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDeseado = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date fecha = formatoActual.parse(fechaString);
            String fechaFormateada = formatoDeseado.format(fecha);
            Date fechaFormateadaDate = formatoDeseado.parse(fechaFormateada);
            
            return fechaFormateadaDate;
        } catch (ParseException e) {
            return null;
        }
    }
    
    // Suma 30 días a una fecha dada y devuelve la nueva fecha
    public static Date sumar30Dias(Date fechaActual) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date nuevaFecha = calendar.getTime();
        return nuevaFecha;
    }
    
    // Calcula la fecha hace 30 días a partir de la fecha actual
    public static Date calcularFechaHace30Dias() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date fechaHace30Dias = calendar.getTime();
        return fechaHace30Dias;
    }
}
