package controladores.empleados;

import com.google.gson.Gson;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.PrestamoDAO;
import modelo.entidades.Libro;
import modelo.entidades.Prestamo;
import modelo.utils.JpaUtils;

@WebServlet(name = "ConsultarPrestamos", urlPatterns = {"/ConsultarPrestamos"})
public class ConsultarPrestamos extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los parámetros de fecha del formulario
        String fechaInicioStr = request.getParameter("fechaInicio");
        String fechaFinStr = request.getParameter("fechaFin");

        try {
            // Verificar si las fechas no están vacías y no son nulas
            if (!fechaInicioStr.isEmpty() && !fechaFinStr.isEmpty() && fechaInicioStr != null && fechaFinStr != null) {
                // Obtener el EntityManager y convertir las cadenas de fecha en objetos Date
                EntityManager entityManager = JpaUtils.getEntityManager();
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaInicio = formatoFecha.parse(fechaInicioStr);
                Date fechaFin = formatoFecha.parse(fechaFinStr);

                PrestamoDAO prestamoController = new PrestamoDAO(entityManager);

                // Obtener los préstamos y libros prestados entre las fechas proporcionadas
                List<Prestamo> listaPrestamos = prestamoController.obtenerPrestamosEntreFechas(fechaInicio, fechaFin);
                List<Libro> librosPrestados = prestamoController.obtenerLibrosPrestadosEntreFechas(fechaInicio, fechaFin);

                // Organizar los datos para el gráfico
                List<String> nombresLibros = new ArrayList<>();
                List<Integer> vecesPrestado = new ArrayList<>();

                for (Libro libro : librosPrestados) {
                    nombresLibros.add(libro.getTitulo());
                    vecesPrestado.add((int) prestamoController.contadorVecesPrestadoUnLibroEntreFechas(fechaInicio, fechaFin, libro));
                }

                Gson gson = new Gson();
                String nombresLibrosJson = gson.toJson(nombresLibros);
                String vecesPrestadoJson = gson.toJson(vecesPrestado);

                // Establecer atributos para la vista
                request.setAttribute("listaPrestamos", listaPrestamos);
                request.setAttribute("nombresLibros", nombresLibrosJson);
                request.setAttribute("vecesPrestado", vecesPrestadoJson);

                // Redireccionar a la vista de consulta de préstamos
                request.getRequestDispatcher("pages/empleados/consultarPrestamos.jsp").forward(request, response);
            } else {
                // Si las fechas están vacías o nulas, llamar al método doGet()
                doGet(request, response);
            }
        } catch (ParseException e) {
            // Manejar errores de análisis de fecha
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener los préstamos y pasarlos a la vista
            EntityManager entityManager = JpaUtils.getEntityManager();
            PrestamoDAO prestamoController = new PrestamoDAO(entityManager);
            List<Prestamo> listaPrestamos = prestamoController.obtenerTodosLosPrestamos();
            request.setAttribute("listaPrestamos", listaPrestamos);

            // Redireccionar a la vista de consulta de préstamos
            request.getRequestDispatcher("pages/empleados/consultarPrestamos.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            // Manejar errores de entrada/salida o de servlet
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
