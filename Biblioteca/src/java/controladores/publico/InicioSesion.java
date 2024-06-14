package controladores.publico;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet(name = "InicioSesion", urlPatterns = {"/InicioSesion"})
public class InicioSesion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el correo electrónico del parámetro de la solicitud
        String email = request.getParameter("email");

        // Establecer el correo electrónico como atributo de solicitud
        request.setAttribute("email", email);

        // Redirigir a la página de inicio de sesión
        request.getRequestDispatcher("pages/publico/inicioSesion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        // Obtener el EntityManager y el controlador de Usuario
        EntityManager entityManager = JpaUtils.getEntityManager();
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);

        // Lista para almacenar errores
        List<String> errores = new ArrayList<>();

        try {
            // Validar al usuario
            Usuario usuario = usuarioController.validarUsuario(email, contrasena);

            if (usuario == null) {
                // Credenciales incorrectas, agregar un mensaje de error
                errores.add("El usuario o la contraseña son incorrectos");
            } else {
                // Establecer el usuario en la sesión
                request.getSession().setAttribute("usuario", usuario);

                // Redirigir según el tipo de usuario
                switch (usuario.getTipoUsuario()) {
                    case "ADMIN":
                        response.sendRedirect("PanelAdministrador");
                        break;
                    case "SOCIO":
                        response.sendRedirect("PanelUsuario");
                        break;
                    case "EMPLEADO":
                        response.sendRedirect("PanelEmpleado");
                        break;
                }
            }
        } catch (IOException e) {
            // Manejar errores de entrada/salida
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        // Si hay errores o el usuario no se redirigió, mostrar el formulario de inicio de sesión
        if (!errores.isEmpty()) {
            request.setAttribute("email", email);
            request.setAttribute("errores", errores);
            request.getRequestDispatcher("pages/publico/inicioSesion.jsp").forward(request, response);
        }
    }
}
