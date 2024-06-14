package controladores.publico;

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

@WebServlet(name = "Index", urlPatterns = {"/Index"})
public class Index extends HttpServlet {

    // Método para manejar las solicitudes GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el EntityManager y el controlador de Libro
        EntityManager entityManager = JpaUtils.getEntityManager();
        LibroDAO libroController = new LibroDAO(entityManager);

        try {
            // Obtener todos los libros de la base de datos
            List<Libro> listaLibros = libroController.obtenerTodosLosLibros();

            // Establecer la lista de libros como un atributo de solicitud
            request.setAttribute("listaLibros", listaLibros);

            // Redirigir a la página principal (index.jsp)
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            // Manejar errores de entrada/salida o de servlet
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            // Cerrar el EntityManager después de su uso
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
