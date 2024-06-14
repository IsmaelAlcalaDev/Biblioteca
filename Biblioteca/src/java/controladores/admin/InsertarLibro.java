package controladores.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.utils.JpaUtils;
import modelo.dao.LibroDAO;
import modelo.entidades.Libro;
import modelo.utils.FechaUtils;
import modelo.utils.GenerosUtils;
import modelo.utils.ImagenUtils;
import modelo.utils.ValidacionUtils;

@WebServlet(name = "InsertarLibro", urlPatterns = {"/InsertarLibro"})
public class InsertarLibro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Establecer los atributos necesarios para el formulario de inserción de libros
        request.setAttribute("arrayGeneros", GenerosUtils.obtenerGenerosLiterarios());
        // Redirigir al formulario de inserción de libros
        request.getRequestDispatcher("pages/administrador/insertarLibro.jsp").forward(request, response); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String[] generos = request.getParameterValues("genero[]");
        String autor = request.getParameter("autor");
        String fechaEdicion = request.getParameter("fechaEdicion");
        String numEjemplares = request.getParameter("numEjemplares");

        try {
            // Obtener la imagen de portada
            Part imagenPortada = request.getPart("imagen");
            // Persistir el libro en la base de datos
            EntityManager entityManager = JpaUtils.getEntityManager();
            LibroDAO libroController = new LibroDAO(entityManager);
            Libro libroExistente = libroController.buscarLibroPorISBN(isbn);
            List<String> errores = new ArrayList<>();
            if (libroExistente != null) {
                errores.add("Ya existe un libro con el ISBN " + isbn);
            }
            // Validar el ISBN
            boolean isbnValido = ValidacionUtils.validarISBN(isbn);

            if(Integer.parseInt(numEjemplares) == 0){
                errores.add("Introduzca un libro con al menos un ejemplar");
            }
            if (!isbnValido) {
                errores.add("Introduce un ISBN válido");
            } 

            if (errores.isEmpty()) {
                // Convertir la fecha de edición
                Date fechaEdicionConvertida = FechaUtils.convertirAFecha(fechaEdicion);
                // Crear una cadena de géneros literarios
                String cadenaGeneros = GenerosUtils.crearCadenaDeGeneros(generos);
                // Nombre de la imagen de portada
                String nombreImagen = "portada_" + isbn + ".jpg";
                // Crear un nuevo objeto Libro con los datos proporcionados
                Libro nuevoLibro = new Libro(isbn, titulo, fechaEdicionConvertida, nombreImagen, Integer.parseInt(numEjemplares), autor, cadenaGeneros);
                libroController.guardarLibro(nuevoLibro);

                ImagenUtils.guardarImagen(imagenPortada, nombreImagen);

                request.setAttribute("mensaje", "Libro añadido correctamente");
            }else{
                request.setAttribute("errores", errores);
            }
        } catch (IOException | NumberFormatException | ServletException e) {
            // Manejar excepciones de E/S y de servlet
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        // Obtener los géneros literarios y redirigir al formulario de inserción de libros
        request.setAttribute("arrayGeneros", GenerosUtils.obtenerGenerosLiterarios());
        request.getRequestDispatcher("pages/administrador/insertarLibro.jsp").forward(request, response); 
    }
}
