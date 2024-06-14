package controladores.empleados;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;
import modelo.utils.JpaUtils;

@WebServlet(name = "PanelEmpleado", urlPatterns = {"/PanelEmpleado"})
public class PanelEmpleado extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager entityManager = JpaUtils.getEntityManager();
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);

        try {
            // Obtener la lista de socios con préstamos expirados
            List<Usuario> listaSocios = usuarioController.buscarSociosConPrestamosExpirados();
            // Establecer atributos y redirigir al panel del empleado
            request.setAttribute("listaSocios", listaSocios);
            request.getRequestDispatcher("pages/empleados/panelEmpleado.jsp").forward(request, response);   
        } catch (Exception e) {
            // Manejar excepciones
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {
            // Cerrar el EntityManager después de su uso
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager entityManager = JpaUtils.getEntityManager();
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);

        try {
            // Obtener la lista de socios expirados
            List<Usuario> listaSocios = usuarioController.buscarSociosConPrestamosExpirados();
            System.out.println("Lista de socios antes de la actualización: " + listaSocios);

            // Marcar los socios como sancionados
            for (Usuario socio : listaSocios) {
                socio.setSancionado(true);
                usuarioController.actualizarUsuario(socio);
            }

            // Volver a obtener la lista actualizada de socios
            listaSocios = usuarioController.buscarSociosConPrestamosExpirados();
            System.out.println("Lista de socios después de la actualización: " + listaSocios);

            // Pasar la lista actualizada al JSP
            request.setAttribute("listaSocios", listaSocios);
            request.getRequestDispatcher("pages/empleados/panelEmpleado.jsp").forward(request, response);

        } catch (Exception e) {
            // Manejar excepciones
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

