package modelo.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static modelo.utils.JpaUtils.getEntityManager;
import modelo.dao.exceptions.NonexistentEntityException;
import modelo.entidades.Libro;
import modelo.entidades.Prestamo;
import modelo.entidades.Usuario;
import modelo.utils.FechaUtils;

public class PrestamoDAO implements Serializable {

    private final EntityManager entityManager;

    public PrestamoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Crea un nuevo préstamo en la base de datos
    public void crearPrestamo(Prestamo prestamo) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(prestamo);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    // Actualiza la información de un préstamo en la base de datos
    public void actualizarPrestamo(Prestamo prestamo) throws Exception {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(prestamo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw ex;
        }
    }

    // Elimina un préstamo de la base de datos por su ID
    public void eliminarPrestamo(Integer prestamoID) throws NonexistentEntityException {
        entityManager.getTransaction().begin();
        try {
            Prestamo prestamo = entityManager.find(Prestamo.class, prestamoID);
            if (prestamo == null) {
                throw new NonexistentEntityException("El prestamo con ID " + prestamoID + " no existe.");
            }
            entityManager.remove(prestamo);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    // Actualiza la fecha de devolución de un préstamo en la base de datos
    public void actualizarFechaDevolucion(int prestamoID) {
        EntityManager em = getEntityManager();
        Prestamo prestamo = em.find(Prestamo.class, prestamoID);

        prestamo.setFechaDevolucion(new Date());

        try {
            em.getTransaction().begin();
            em.merge(prestamo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    // Obtiene todos los préstamos de un usuario ordenados por fecha de devolución
    public List<Prestamo> obtenerPrestamosPorUsuario(Usuario usuario) {
        return entityManager.createQuery("SELECT p FROM Prestamo p WHERE p.usuarioID = :usuario ORDER BY p.fechaDevolucion ASC")
                .setParameter("usuario", usuario)
                .getResultList();
    }

    // Busca un préstamo por su ID
    public Prestamo buscarPrestamo(Prestamo prestamo) {
        return entityManager.find(Prestamo.class, prestamo.getPrestamoID());
    }

    // Verifica si existe un préstamo repetido para un usuario y un libro dado
    public boolean existePrestamoRepetido(Usuario usuario, Libro libro) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM Prestamo p WHERE p.usuarioID = :usuario AND p.libroISBN = :libro AND p.fechaDevolucion IS NULL",
                Long.class
        );
        query.setParameter("usuario", usuario);
        query.setParameter("libro", libro);

        return query.getSingleResult() > 0;
    }

    // Obtiene todos los préstamos de la base de datos
    public List<Prestamo> obtenerTodosLosPrestamos() {
        TypedQuery<Prestamo> query = entityManager.createNamedQuery("Prestamo.findAll", Prestamo.class);
        return query.getResultList();
    }

    // Obtiene todos los préstamos de un libro específico
    public List<Prestamo> obtenerPrestamosPorLibro(Libro libro) {
        TypedQuery<Prestamo> query = entityManager.createNamedQuery("Prestamo.findByLibroISBN", Prestamo.class);
        query.setParameter("libroISBN", libro);
        return query.getResultList();
    }

    // Obtiene todos los préstamos no devueltos
    public List<Prestamo> obtenerPrestamosPrestados() {
        TypedQuery<Prestamo> query = entityManager.createQuery(
                "SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NULL ORDER BY p.fechaPrestamo", Prestamo.class);
        return query.getResultList();
    }

    // Obtiene todos los préstamos devueltos
    public List<Prestamo> obtenerPrestamosDevueltos() {
        TypedQuery<Prestamo> query = entityManager.createQuery(
                "SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NOT NULL ORDER BY p.fechaPrestamo", Prestamo.class);
        return query.getResultList();
    }

    // Obtiene todos los préstamos devueltos de un libro específico
    public List<Prestamo> obtenerPrestamosDevueltosPorLibro(String isbnLibro) {
        TypedQuery<Prestamo> query = entityManager.createQuery(
                "SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NOT NULL AND p.libroISBN.isbn = :isbnLibro ORDER BY p.fechaPrestamo", Prestamo.class);
        query.setParameter("isbnLibro", isbnLibro);
        return query.getResultList();
    }

    // Obtiene todos los préstamos no devueltos de un libro específico
    public List<Prestamo> obtenerPrestamosPrestadosPorLibro(String isbnLibro) {
        TypedQuery<Prestamo> query = entityManager.createQuery(
                "SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NULL AND p.libroISBN.isbn = :isbnLibro ORDER BY p.fechaPrestamo", Prestamo.class);
        query.setParameter("isbnLibro", isbnLibro);
        return query.getResultList();
    }

    // Obtiene los correos electrónicos de los usuarios con préstamos vencidos
    public List<String> obtenerEmailsUsuariosConPrestamosVencidos() {
        Date fechaLimite = FechaUtils.calcularFechaHace30Dias();
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT DISTINCT u.correoElectronico FROM Usuario u "
                + "JOIN u.prestamoCollection p "
                + "WHERE p.fechaDevolucion IS NULL "
                + "AND p.fechaPrestamo < :fechaLimite", String.class);

        query.setParameter("fechaLimite", fechaLimite);

        return query.getResultList();
    }

    // Encuentra préstamos no devueltos de un libro por su ISBN
    public List<Prestamo> encontrarPrestamosPrestadosPorISBN(String isbn) {
        TypedQuery<Prestamo> query = entityManager.createQuery(
                "SELECT p FROM Prestamo p WHERE p.libroISBN.isbn = :isbn AND p.fechaDevolucion IS NULL", Prestamo.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }

    // Obtiene libros prestados entre un rango de fechas
    public List<Libro> obtenerLibrosPrestadosEntreFechas(Date fechaInicio, Date fechaFin) {
        TypedQuery<Libro> query = entityManager.createQuery(
                "SELECT DISTINCT l FROM Prestamo p JOIN p.libroISBN l "
                + "WHERE p.fechaPrestamo BETWEEN :fechaInicio AND :fechaFin", Libro.class);

        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);

        return query.getResultList();
    }

    // Cuenta las veces que un libro ha sido prestado entre un rango de fechas
    public long contadorVecesPrestadoUnLibroEntreFechas(Date fechaInicio, Date fechaFin, Libro libro) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM Prestamo p "
                + "WHERE p.libroISBN = :libro "
                + "AND p.fechaPrestamo BETWEEN :fechaInicio AND :fechaFin", Long.class);

        query.setParameter("libro", libro);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);

        return query.getSingleResult();
    }

    // Obtiene todos los préstamos entre un rango de fechas
    public List<Prestamo> obtenerPrestamosEntreFechas(Date fechaInicio, Date fechaFin) {
        TypedQuery<Prestamo> query = entityManager.createQuery(
                "SELECT p FROM Prestamo p "
                + "WHERE p.fechaPrestamo BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fechaPrestamo DESC", Prestamo.class);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
}