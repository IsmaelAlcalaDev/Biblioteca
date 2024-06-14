package controladores.admin;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.PrestamoDAO;
import modelo.utils.EmailUtils;
import modelo.utils.JpaUtils;

@WebServlet(name = "EnviarEmailASocio", urlPatterns = {"/EnviarEmailASocio"})
public class EnviarEmailASocio extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        // Obtener los parámetros de la solicitud
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        try {
            // Obtener el EntityManager y el controlador de préstamos
            EntityManager entityManager = JpaUtils.getEntityManager();
            PrestamoDAO prestamoController = new PrestamoDAO(entityManager);

            // Obtener los destinatarios de los correos electrónicos
            List<String> destinatarios = prestamoController.obtenerEmailsUsuariosConPrestamosVencidos();

            if (destinatarios.isEmpty()) {
                // Si no hay usuarios para notificar, establecer un mensaje de error
                request.setAttribute("error", "No hay usuarios para notificar");
            } else {
                // Enviar notificaciones por correo electrónico
                EmailUtils emailUtils = new EmailUtils();
                emailUtils.enviarNotificacionesPorEmail(destinatarios, email, contrasena);
                // Establecer un mensaje de éxito si se envían las notificaciones correctamente
                request.setAttribute("mensaje", "Notificación enviada correctamente a " + destinatarios.size() + " socios");
            }
        } catch (Exception e) {
            // Manejar excepciones generales
            request.setAttribute("error", "Ocurrió un error inesperado");
        }
        // Redireccionar a la gestión de préstamos
        request.getRequestDispatcher("GestionPrestamos").forward(request, response);
    }
}
