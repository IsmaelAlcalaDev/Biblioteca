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
import modelo.dao.LibroDAO;
import modelo.dao.PrestamoDAO;
import modelo.entidades.Libro;
import modelo.entidades.Prestamo;
import modelo.utils.JpaUtils;

@WebServlet(name = "FiltrarPrestamos", urlPatterns = {"/FiltrarPrestamos"})
public class FiltrarPrestamos extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        // Obtener parámetros de la solicitud
        String isbn = request.getParameter("isbn");
        String estado = request.getParameter("estado");
        EntityManager entityManager = JpaUtils.getEntityManager();
        PrestamoDAO prestamoController = new PrestamoDAO(entityManager);
        LibroDAO libroController = new LibroDAO(entityManager);
        Libro libro = libroController.buscarLibroPorISBN(isbn);
        isbn = (isbn == null) ? "" : isbn;
        estado = (estado == null) ? "" : estado;

        List<Prestamo> listaPrestamos = new ArrayList<>();
        List<Libro> listaLibros = libroController.obtenerTodosLosLibros();

        // Aplicar filtros según los parámetros recibidos
        if (isbn.isEmpty() && estado.isEmpty()) {
            listaPrestamos = prestamoController.obtenerTodosLosPrestamos();
        } else if (!isbn.isEmpty() && estado.isEmpty()) {
            if (isbn.equals("mostrarTodos")) {
                listaPrestamos = prestamoController.obtenerTodosLosPrestamos();
            } else {
                listaPrestamos = prestamoController.obtenerPrestamosPorLibro(libro);
            }
        } else if (isbn.isEmpty() && !estado.isEmpty()) {
            if (estado.equals("todos")) {
                listaPrestamos = prestamoController.obtenerTodosLosPrestamos();
            } else if (estado.equals("prestados")) {
                listaPrestamos = prestamoController.obtenerPrestamosPrestados();
            } else if (estado.equals("devueltos")) {
                listaPrestamos = prestamoController.obtenerPrestamosDevueltos();
            }
        } else if (!isbn.isEmpty() && !estado.isEmpty()) {
            if (isbn.equals("mostrarTodos") && estado.equals("todos")) {
                listaPrestamos = prestamoController.obtenerTodosLosPrestamos();
            } else if (isbn.equals("mostrarTodos") && estado.equals("prestados")) {
                listaPrestamos = prestamoController.obtenerPrestamosPrestados();
            } else if (isbn.equals("mostrarTodos") && estado.equals("devueltos")) {
                listaPrestamos = prestamoController.obtenerPrestamosDevueltos();
            } else if (!isbn.equals("mostrarTodos") && estado.equals("todos")) {
                listaPrestamos = prestamoController.obtenerPrestamosPorLibro(libro);
            } else if (!isbn.equals("mostrarTodos") && estado.equals("prestados")) {
                listaPrestamos = prestamoController.obtenerPrestamosPrestadosPorLibro(isbn);
            } else if (!isbn.equals("mostrarTodos") && estado.equals("devueltos")) {
                listaPrestamos = prestamoController.obtenerPrestamosDevueltosPorLibro(isbn);
            }
        }

        // Establecer atributos y redireccionar a la gestión de préstamos
        request.setAttribute("listaPrestamos", listaPrestamos);
        request.setAttribute("listaLibros", listaLibros);
        request.getRequestDispatcher("pages/administrador/gestionPrestamos.jsp").forward(request, response);
    }
}
