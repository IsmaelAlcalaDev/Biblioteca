package controladores.empleados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.utils.JpaUtils;
import modelo.dao.LibroDAO;
import modelo.dao.PrestamoDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Libro;
import modelo.entidades.Prestamo;
import modelo.entidades.Usuario;
import modelo.utils.FechaUtils;

@WebServlet(name = "RealizarPrestamo", urlPatterns = {"/RealizarPrestamo"})
public class RealizarPrestamo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el EntityManager y los controladores de Libro y Usuario
        EntityManager entityManager = JpaUtils.getEntityManager();
        LibroDAO libroController = new LibroDAO(entityManager);
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);

        // Obtener la lista de libros disponibles y socios no sancionados
        List<Libro> librosDisponibles = libroController.buscarLibrosConEjemplaresDisponibles();
        List<Usuario> listaSocios = usuarioController.buscarSociosNoSancionados("SOCIO");

        // Establecer atributos y redireccionar a la página de realizar préstamo
        request.setAttribute("listaLibros", librosDisponibles);
        request.setAttribute("listaSocios", listaSocios);
        request.getRequestDispatcher("pages/empleados/realizarPrestamo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String isbn = request.getParameter("libro");
        String idSocio = request.getParameter("socio");

        // Obtener el EntityManager y los controladores de Libro, Usuario y Prestamo
        EntityManager entityManager = JpaUtils.getEntityManager();
        LibroDAO libroController = new LibroDAO(entityManager);
        UsuarioDAO usuarioController = new UsuarioDAO(entityManager);
        PrestamoDAO prestamoController = new PrestamoDAO(entityManager);
        List<String> errores = new ArrayList<>();
        try {
            // Buscar el libro y el socio correspondientes a los parámetros
            Libro libroExistente = libroController.buscarLibroPorISBN(isbn);
            Usuario socioExistente = usuarioController.obtenerUsuarioPorID(Integer.valueOf(idSocio));

            // Verificar si el socio ya tiene prestado el libro
            boolean tienePrestamoRepetido = prestamoController.existePrestamoRepetido(socioExistente, libroExistente);
            if (tienePrestamoRepetido) {
                errores.add("Este socio ya tiene este libro");
            }

            if (errores.isEmpty()) {
                // Realizar el préstamo y actualizar la cantidad de ejemplares del libro
                Date fechaActual = new Date();
                Date fechaDevolucion = FechaUtils.sumar30Dias(fechaActual);
                Prestamo nuevoPrestamo = new Prestamo(fechaActual, libroExistente, socioExistente);
                int numEjemplares = libroExistente.getNumEjemplares();
                libroExistente.setNumEjemplares(numEjemplares - 1);
                prestamoController.crearPrestamo(nuevoPrestamo);
                Prestamo prestamoExistente = prestamoController.buscarPrestamo(nuevoPrestamo);

                // Establecer atributos para la vista
                request.setAttribute("prestamoRealizado", true);
                request.setAttribute("libro", libroExistente);
                request.setAttribute("socio", socioExistente);
                request.setAttribute("prestamo", prestamoExistente);
                request.setAttribute("fechaDevolucion", fechaDevolucion);
            } else {
                // Si hay errores, establecerlos como atributos
                request.setAttribute("errores", errores);
            }       

            // Obtener nuevamente la lista de libros disponibles y socios no sancionados
            List<Libro> librosDisponibles = libroController.buscarLibrosConEjemplaresDisponibles();
            List<Usuario> listaSocios = usuarioController.buscarSociosNoSancionados("SOCIO");

            // Establecer atributos y redireccionar a la página de realizar préstamo
            request.setAttribute("listaLibros", librosDisponibles);
            request.setAttribute("listaSocios", listaSocios);
            request.getRequestDispatcher("pages/empleados/realizarPrestamo.jsp").forward(request, response);
        } catch (IOException | NumberFormatException | ServletException e) {
            // Manejar errores de entrada/salida o de servlet
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
