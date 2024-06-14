package controladores.admin;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.utils.JpaUtils;
import modelo.dao.UsuarioDAO;

@WebServlet(name = "EliminarEmpleado", urlPatterns = {"/EliminarEmpleado"})
public class EliminarEmpleado extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el EntityManager y el controlador de usuarios
        EntityManager entityManager = JpaUtils.getEntityManager();
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);

        // Obtener el ID del empleado a eliminar desde la solicitud
        String idEmpleado = request.getParameter("id");

        try {
            // Intentar eliminar al usuario con el ID proporcionado
            usuarioController.eliminarUsuario(Integer.valueOf(idEmpleado));
        } catch (NumberFormatException e) {
            // Manejar excepciones de formato numérico
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        // Redireccionar a la gestión de empleados
        response.sendRedirect("GestionEmpleados");
    }
}
