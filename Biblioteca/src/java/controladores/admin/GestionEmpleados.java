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
import modelo.dao.UsuarioDAO;

import modelo.entidades.Usuario;

@WebServlet(name = "GestionEmpleados", urlPatterns = {"/GestionEmpleados"})
public class GestionEmpleados extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el EntityManager y el controlador de usuarios
        EntityManager entityManager = JpaUtils.getEntityManager();
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);
        try {
            // Buscar empleados con roles "EMPLEADO" y "ADMIN"
            List<Usuario> empleados = usuarioController.buscarEmpleados("EMPLEADO", "ADMIN");
            // Establecer atributo de la solicitud con la lista de empleados encontrados
            request.setAttribute("empleados", empleados);
        } catch(Exception e) {
            // Manejar excepciones generales
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        // Redireccionar a la página de gestión de empleados
        request.getRequestDispatcher("pages/administrador/gestionEmpleados.jsp").forward(request, response); 
    }
}
