package controladores.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

@WebServlet(name = "EditarLibro", urlPatterns = {"/EditarLibro"})
public class EditarLibro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el ISBN del libro desde la solicitud
        String isbnLibro = request.getParameter("id");

        // Obtener el EntityManager y el controlador de libros
        EntityManager entityManager = JpaUtils.getEntityManager();
        LibroDAO libroController = new LibroDAO(entityManager);

        try {
            // Buscar el libro por su ISBN
            Libro libro = libroController.buscarLibroPorISBN(isbnLibro);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = sdf.format(libro.getFechaEdicion());

            // Establecer atributos para la vista
            request.setAttribute("fechaFormateada", fechaFormateada);
            request.setAttribute("libro", libro);
            request.setAttribute("arrayGeneros", GenerosUtils.obtenerGenerosLiterarios());
            request.setAttribute("arrayGenerosSeleccionados", GenerosUtils.crearArrayDeGeneros(libro.getGenero()));
            request.setAttribute("isbn", isbnLibro);

            // Redirigir a la página de edición de libros
            request.getRequestDispatcher("pages/administrador/editarLibro.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            // Manejar excepciones
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Obtener parámetros de la solicitud
        String isbnLibro = request.getParameter("id");
        String nuevoIsbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String[] generos = request.getParameterValues("genero[]");
        String autor = request.getParameter("autor");
        String fechaEdicion = request.getParameter("fechaEdicion");
        String numEjemplares = request.getParameter("numEjemplares");

        try {
            // Obtener la parte de la imagen de portada
            Part imagenPortada = request.getPart("imagen");
            EntityManager entityManager = JpaUtils.getEntityManager();
            LibroDAO libroController = new LibroDAO(entityManager);
            List<String> errores = new ArrayList<>();
            Libro libroExistente = libroController.buscarLibroPorISBN(isbnLibro);

            // Validar los datos del libro
            if (Integer.parseInt(numEjemplares) == 0) {
                errores.add("El libro debe tener al menos un ejemplar");
            } 
            if (!libroExistente.getIsbn().equals(nuevoIsbn)) {
                errores.add("No se puede modificar el ISBN.");
            }

            if (errores.isEmpty()) {
                // Convertir la fecha de edición y crear cadena de géneros
                Date fechaEdicionConvertida = FechaUtils.convertirAFecha(fechaEdicion);
                String cadenaGeneros = GenerosUtils.crearCadenaDeGeneros(generos);

                // Actualizar los detalles del libro
                libroExistente.setTitulo(titulo);
                libroExistente.setGenero(cadenaGeneros);
                libroExistente.setAutor(autor);
                libroExistente.setFechaEdicion(fechaEdicionConvertida);
                libroExistente.setNumEjemplares(Integer.parseInt(numEjemplares));

                // Guardar la imagen de portada si se proporciona
                if (imagenPortada != null && imagenPortada.getSize() > 0) {
                    String nombreImagen = "portada_" + nuevoIsbn + ".jpg";
                    ImagenUtils.eliminarImagen(isbnLibro);
                    ImagenUtils.guardarImagen(imagenPortada, nombreImagen);
                    libroExistente.setImagenPortada(nombreImagen);
                }
                libroController.actualizarLibro(libroExistente);
                request.setAttribute("mensaje", "Libro editado correctamente");
            } else {
                request.setAttribute("errores", errores);
            }
        } catch (Exception e) {
            // Manejar excepciones
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        doGet(request, response);
        request.getRequestDispatcher("pages/administrador/editarLibro.jsp").forward(request, response);
        
    }
}
