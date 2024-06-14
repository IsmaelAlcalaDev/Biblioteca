package modelo.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Libro;

public class LibroDAO implements Serializable {
    private final EntityManager entityManager;

    public LibroDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Método para guardar un libro en la base de datos
    public void guardarLibro(Libro libro) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(libro);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    // Método para actualizar un libro en la base de datos
    public void actualizarLibro(Libro libro) throws Exception {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(libro);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw ex;
        }
    }

    // Método para eliminar un libro de la base de datos por su ISBN
    public void eliminarLibroPorISBN(String isbn) throws NonexistentEntityException {
        entityManager.getTransaction().begin();
        try {
            Libro libro = entityManager.find(Libro.class, isbn);
            if (libro == null) {
                throw new NonexistentEntityException("El libro con ISBN " + isbn + " no existe.");
            }
            entityManager.remove(libro);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }
    
    // Método para obtener todos los libros de la base de datos
    public List<Libro> obtenerTodosLosLibros() {
        return entityManager.createNamedQuery("Libro.findAll", Libro.class).getResultList();
    }
    
    // Método para obtener todos los libros ordenados por ejemplares disponibles
    public List<Libro> obtenerTodosLosLibrosOrdenadosPorEjemplares() {
        TypedQuery<Libro> query = entityManager.createNamedQuery("Libro.findAllOrderByEjemplar", Libro.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
    // Método para aumentar el número de ejemplares disponibles de un libro
    public void aumentarNumeroDeEjemplares(String isbn) {
        try {
            Libro libro = entityManager.find(Libro.class, isbn);
            if (libro != null) {
                libro.setNumEjemplares(libro.getNumEjemplares() + 1);
                
                entityManager.getTransaction().begin();
                entityManager.merge(libro);
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    // Método para buscar un libro por su ISBN
    public Libro buscarLibroPorISBN(String isbn) {
        TypedQuery<Libro> query = entityManager.createNamedQuery("Libro.findByIsbn", Libro.class);
        query.setParameter("isbn", isbn);
        List<Libro> libros = query.getResultList();
        if (!libros.isEmpty()) {
            return libros.get(0); 
        } else {
            return null; 
        }
    }

    // Método para buscar libros con ejemplares disponibles
    public List<Libro> buscarLibrosConEjemplaresDisponibles() {
        return entityManager.createNamedQuery("Libro.findAllConEjemplares", Libro.class).getResultList();
    }
}
