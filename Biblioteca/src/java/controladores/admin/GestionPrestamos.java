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
import modelo.entidades.Libro;
import modelo.entidades.Prestamo;

@WebServlet(name = "GestionPrestamos", urlPatterns = {"/GestionPrestamos"})
public class GestionPrestamos extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Obtener el EntityManager y los controladores de libros y préstamos
            EntityManager entityManager = JpaUtils.getEntityManager();
            LibroDAO libroController = new LibroDAO(entityManager);
            PrestamoDAO prestamoController = new PrestamoDAO(entityManager);
            // Obtener la lista de libros y préstamos
            List<Libro> listaLibros = libroController.obtenerTodosLosLibros();
            List<Prestamo> listaPrestamos = prestamoController.obtenerTodosLosPrestamos();
            // Establecer atributos de la solicitud con las listas de libros y préstamos
            request.setAttribute("listaPrestamos", listaPrestamos);
            request.setAttribute("listaLibros", listaLibros);
            // Redireccionar a la página de gestión de préstamos
            request.getRequestDispatcher("pages/administrador/gestionPrestamos.jsp").forward(request, response);    
        } catch (IOException | ServletException e) {
            // Manejar excepciones de E/S y de servlet
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
