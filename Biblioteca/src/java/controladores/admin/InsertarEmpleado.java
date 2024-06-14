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

@WebServlet(name = "InsertarEmpleado", urlPatterns = {"/InsertarEmpleado"})
public class InsertarEmpleado extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirigir al formulario de inserción de empleado
        request.getRequestDispatcher("pages/administrador/insertarEmpleado.jsp").forward(request, response); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String repetirContrasena = request.getParameter("repetirContrasena");
        String domicilio = request.getParameter("domicilio");
        String tipoUsuario = request.getParameter("tipoUsuario");

        EntityManager entityManager = JpaUtils.getEntityManager();
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);
        List<String> errores = new ArrayList<>();

        // Verificar si las contraseñas coinciden
        if (!contrasena.equals(repetirContrasena)) {
            errores.add("Las contraseñas no coinciden");
        }
        Usuario usuarioExistente = usuarioController.buscarUsuarioPorCorreo(email);
        if (usuarioExistente != null) {
            errores.add("Este email ya está en uso");
        } 
        if(errores.isEmpty()){
           // Crear un nuevo empleado
           Usuario nuevoEmpleado = new Usuario(nombre, apellidos, email, contrasena, domicilio, tipoUsuario);
            // Persistir el usuario en la base de datos
            usuarioController.registrarUsuario(nuevoEmpleado);
            request.setAttribute("mensaje", "Empleado creado correctamente"); 
        } else {
            request.setAttribute("errores", errores);
        }
        // Redirigir nuevamente al formulario de inserción de empleado
        request.getRequestDispatcher("pages/administrador/insertarEmpleado.jsp").forward(request, response); 
    }
}
