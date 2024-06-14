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
import modelo.entidades.Libro;

@WebServlet(name = "GestionLibros", urlPatterns = {"/GestionLibros"})
public class GestionLibros extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Obtener el EntityManager y el controlador de libros
            EntityManager entityManager = JpaUtils.getEntityManager();
            LibroDAO libroController = new LibroDAO(entityManager);
            // Obtener la lista de libros ordenados por ejemplares
            List<Libro> listaLibros = libroController.obtenerTodosLosLibrosOrdenadosPorEjemplares();
            // Establecer atributo de la solicitud con la lista de libros
            request.setAttribute("listaLibros", listaLibros);
            // Redireccionar a la página de gestión de libros
            request.getRequestDispatcher("pages/administrador/gestionLibros.jsp").forward(request, response); 
        } catch (IOException | ServletException e) {
            // Manejar excepciones de E/S y de servlet
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); 
    }
}
