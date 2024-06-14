package controladores.admin;

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

@WebServlet(name = "EditarEmpleado", urlPatterns = {"/EditarEmpleado"})
public class EditarEmpleado extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtiene una instancia del EntityManager para interactuar con la base de datos
        EntityManager entityManager = JpaUtils.getEntityManager();
        // Inicializa el controlador de UsuarioDAO utilizando el EntityManager
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);
        // Obtiene el ID del empleado de los parámetros de la solicitud
        String idEmpleado = request.getParameter("id");
        try {
            // Obtiene el empleado por su ID
            Usuario empleado = usuarioController.obtenerUsuarioPorID(Integer.valueOf(idEmpleado));
            // Establece el empleado como atributo de la solicitud para pasar al JSP de edición
            request.setAttribute("empleado", empleado);
            // Reenvía la solicitud y la respuesta al JSP de edición de empleado
            request.getRequestDispatcher("pages/administrador/editarEmpleado.jsp").forward(request, response);
        } catch (IOException | NumberFormatException | ServletException e) {
            // En caso de excepción, redirige a la página de error
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        // Obtiene una instancia del EntityManager para interactuar con la base de datos
        EntityManager entityManager = JpaUtils.getEntityManager();
        // Inicializa el controlador de UsuarioDAO utilizando el EntityManager
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);

        // Obtiene los parámetros del formulario de edición de empleado
        String idEmpleado = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String repetirContrasena = request.getParameter("repetirContrasena");
        String domicilio = request.getParameter("domicilio");
        String tipoUsuario = request.getParameter("tipoUsuario");

        try {
            List<String> errores = new ArrayList<>();
            // Obtiene el empleado por su ID
            Usuario empleado = usuarioController.obtenerUsuarioPorID(Integer.valueOf(idEmpleado));
            // Busca si ya existe un usuario con el correo electrónico proporcionado
            Usuario usuarioExistente = usuarioController.buscarUsuarioPorCorreo(email);

            // Valida si ya existe un usuario con el mismo correo electrónico
            if (!(usuarioExistente == null || usuarioExistente.getUsuarioID().equals(empleado.getUsuarioID()))) {
                errores.add("Ya existe un usuario con este email");
            }
            // Valida si las contraseñas coinciden
            if (!contrasena.equals(repetirContrasena)) {
                errores.add("Las contraseñas no coinciden");
            }

            // Si no hay errores, actualiza la información del empleado en la base de datos
            if (errores.isEmpty()) {
                empleado.setNombre(nombre);
                empleado.setApellidos(apellidos);
                empleado.setCorreoElectronico(email);
                empleado.setContrasena(contrasena);
                empleado.setDomicilio(domicilio);
                empleado.setTipoUsuario(tipoUsuario);
                usuarioController.actualizarUsuario(empleado);
                // Redirige a la página de gestión de empleados después de la actualización
                response.sendRedirect("GestionEmpleados");
                return;
            } else {
                // Si hay errores, establece los errores como atributo de la solicitud para mostrarlos en el JSP
                request.setAttribute("errores", errores);
            }
        } catch (Exception e) {
            // En caso de excepción, redirige a la página de error
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        // Vuelve a cargar la página de edición de empleado con los datos actuales
        try {
            Usuario empleado = usuarioController.obtenerUsuarioPorID(Integer.valueOf(idEmpleado));
            request.setAttribute("empleado", empleado);
            request.getRequestDispatcher("pages/administrador/editarEmpleado.jsp").forward(request, response);
        } catch (IOException | NumberFormatException | ServletException e) {
            // En caso de excepción, redirige a la página de error
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
