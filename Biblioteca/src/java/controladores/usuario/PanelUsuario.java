package controladores.usuario;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.utils.JpaUtils;
import modelo.dao.LibroDAO;
import modelo.dao.PrestamoDAO;
import modelo.entidades.Prestamo;
import modelo.entidades.Usuario;

@WebServlet(name = "PanelUsuario", urlPatterns = {"/PanelUsuario"})
public class PanelUsuario extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession();
        // Crear EntityManager y controladores DAO necesarios
        EntityManager entityManager = JpaUtils.getEntityManager();
        PrestamoDAO prestamoController = new PrestamoDAO(entityManager);
        LibroDAO libroController = new LibroDAO(entityManager);
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        // Obtener el ID del préstamo y el ISBN del libro
        String idPrestamo = request.getParameter("id");
        String isbn = request.getParameter("isbn");

        // Verificar si se proporcionó un ID de préstamo válido
        if (idPrestamo != null && !idPrestamo.isEmpty()) {
            try {
                // Actualizar la fecha de devolución del préstamo y aumentar el número de ejemplares del libro devuelto
                prestamoController.actualizarFechaDevolucion(Integer.parseInt(idPrestamo));
                libroController.aumentarNumeroDeEjemplares(isbn);
            } catch (NumberFormatException e) {
                // Manejar errores de formato de número
                request.setAttribute("exception", e);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } 
        }
        // Obtener la lista de préstamos del usuario
        List<Prestamo> prestamos = prestamoController.obtenerPrestamosPorUsuario(usuario);

        // Pasar la lista de préstamos a la JSP
        request.setAttribute("prestamos", prestamos);  
        request.getRequestDispatcher("pages/usuarios/panelUsuario.jsp").forward(request, response); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
