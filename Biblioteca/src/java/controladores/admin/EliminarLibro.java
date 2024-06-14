package controladores.admin;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.utils.JpaUtils;
import modelo.dao.LibroDAO;
import modelo.dao.PrestamoDAO;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Prestamo;
import modelo.utils.ImagenUtils;

@WebServlet(name = "EliminarLibro", urlPatterns = {"/EliminarLibro"})
public class EliminarLibro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el EntityManager y los controladores de libros y préstamos
        EntityManager entityManager = JpaUtils.getEntityManager();
        LibroDAO libroController = new LibroDAO(entityManager);
        PrestamoDAO prestamoController = new PrestamoDAO(entityManager);

        // Obtener el ISBN del libro desde la solicitud
        String isbnLibro = request.getParameter("id");

        // Obtener la lista de préstamos asociados al libro
        List<Prestamo> prestamosDelLibro = prestamoController.encontrarPrestamosPrestadosPorISBN(isbnLibro);

        if (!prestamosDelLibro.isEmpty()) {
            // Si el libro tiene préstamos asociados sin devolver, mostrar un mensaje de error
            request.setAttribute("error", "Este libro no se puede eliminar porque tiene préstamos asociados sin devolver");
            request.getRequestDispatcher("GestionLibros").forward(request, response);
        } else {
            try {
                // Intentar eliminar el libro y su imagen asociada
                libroController.eliminarLibroPorISBN(isbnLibro);
                ImagenUtils.eliminarImagen(isbnLibro);
            } catch (NonexistentEntityException e) {
                // Manejar excepciones de entidad inexistente
                request.setAttribute("exception", e);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            // Redireccionar a la gestión de libros
            response.sendRedirect("GestionLibros");
        }
    }
}
