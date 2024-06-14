package rest;

import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.dao.LibroDAO;
import modelo.entidades.Libro;
import modelo.utils.JpaUtils;

@Path("/libros")
public class ServicioLibro {
    private final LibroDAO libroController;

    public ServicioLibro() {
        EntityManager entityManager = JpaUtils.getEntityManager();
        this.libroController = new LibroDAO(entityManager);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodosLosLibros() {
        List<Libro> libros = libroController.obtenerTodosLosLibros();
        return Response.ok(libros).build();
    }

    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLibroPorISBN(@PathParam("isbn") String isbn) {
        Libro libro = libroController.buscarLibroPorISBN(isbn);
        if (libro != null) {
            return Response.ok(libro).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                            .entity("Libro con ISBN " + isbn + " no encontrado")
                            .build();
        }
    }
}