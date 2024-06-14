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

@WebServlet(name = "Registro", urlPatterns = {"/Registro"})
public class Registro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir al formulario de registro
        request.getRequestDispatcher("pages/publico/registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String repetirContrasena = request.getParameter("repetirContrasena");
        String domicilio = request.getParameter("domicilio");

        // Crear EntityManager y UsuarioJpaController
        EntityManager entityManager = JpaUtils.getEntityManager();
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);

        // Lista para almacenar errores
        List<String> errores = new ArrayList<>();
        try {
            // Verificar si las contraseñas coinciden
            if (!contrasena.equals(repetirContrasena)) {
                errores.add("Las contraseñas no coinciden");
            } 
            Usuario usuarioExistente = usuarioController.buscarUsuarioPorCorreo(email);
            if (usuarioExistente != null) {
                errores.add("Ya existe un usuario con este email");
            } 
            if(errores.isEmpty()){
                // Crear un nuevo usuario
                String tipoUsuario = "SOCIO";
                Boolean sancionado = false; // Ajusta el valor según corresponda
                Usuario usuario = new Usuario(null, nombre, apellidos, email, contrasena, domicilio, tipoUsuario, sancionado);

                // Persistir el usuario en la base de datos
                usuarioController.registrarUsuario(usuario);

                // Redirigir al inicio de sesión
                response.sendRedirect("InicioSesion");
                return;
            }else{
                // Si hay errores, establecerlos como atributo de solicitud
                request.setAttribute("errores", errores);
            }
        } catch (IOException e) {
            // Manejar errores de entrada/salida
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } 

        // Si hay errores o el registro falla, volver al formulario de registro
        request.getRequestDispatcher("pages/publico/registro.jsp").forward(request, response);  
    }
}
