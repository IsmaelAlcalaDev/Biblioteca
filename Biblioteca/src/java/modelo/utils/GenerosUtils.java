package modelo.utils;

public class GenerosUtils {
    
    // Crea una cadena de géneros literarios a partir de un array de strings
    public static String crearCadenaDeGeneros(String[] generosSeleccionados) {
        StringBuilder generos = new StringBuilder();
        for (int i = 0; i < generosSeleccionados.length; i++) {
            generos.append(generosSeleccionados[i]);

            if (i < generosSeleccionados.length - 1) {
                generos.append(", ");
            }
        }
        return generos.toString();
    }
    
    // Crea un array de géneros literarios a partir de una cadena de géneros separados por coma
    public static String[] crearArrayDeGeneros(String cadenaGeneros) {
        String[] generos = cadenaGeneros.split("\\s*,\\s*");
        return generos;
    }
    
    // Devuelve un array de géneros literarios predefinidos
    public static String[] obtenerGenerosLiterarios() {
        String[] generosLiterarios = {
            "Aventura",
            "Ciencia Ficción",
            "Romance",
            "Misterio",
            "Fantasía",
            "Terror",
            "Drama",
            "Comedia",
            "Acción",
            "Histórico",
            "Policial",
            "Suspense",
            "Infantil",
            "Juvenil",
            "Biografía",
            "Autobiografía",
            "Ensayo",
            "Poesía",
            "Filosofía",
            "Ciencia",
            "Novela Negra",
            "Ficción Contemporánea",
            "Realismo Mágico",
            "Cuento",
            "Teatro",
            "Ensayo Literario",
            "Historia",
            "Literatura Clásica",
            "Literatura Infantil",
            "Literatura Juvenil",
            "Poesía Épica",
            "Poesía Lírica",
            "Poesía Narrativa",
            "Poesía de Amor",
            "Poesía de Protesta",
            "Poesía Metafísica",
            "Poesía Satírica",
            "Poesía Simbolista",
            "Poesía Vanguardista",
            "Poesía Visual",
            "Novela de Aventuras",
            "Novela de Misterio",
            "Novela Histórica",
            "Novela Romántica",
            "Novela Policiaca",
            "Novela de Terror",
            "Novela de Ciencia Ficción",
            "Novela Gótica",
            "Novela Picaresca",
            "Novela Psicológica",
            "Novela Realista",
            "Novela del Oeste",
            "Novela de Espionaje",
            "Novela Gráfica",
            "Novela Satírica",
            "Novela de Aventuras y Misterio",
            "Novela de Formación",
            "Novela de Guerra",
            "Novela de Ciencia Ficción y Fantasía",
            "Novela Corta",
            "Novela Popular",
            "Novela Experimental",
            "Novela de Amor",
            "Novela de Intriga",
            "Novela Histórica y de Aventuras",
            "Novela Epistolar",
            "Novela de Formación y Aventuras",
            "Novela de Ciencia Ficción y Aventuras",
            "Novela Picaresca y de Aventuras",
            "Novela de Aventuras y Fantasía",
            "Novela Romántica y de Aventuras",
            "Novela de Aventuras y Terror",
            "Novela de Aventuras y Ciencia Ficción"
        };
        return generosLiterarios;
    }
}
