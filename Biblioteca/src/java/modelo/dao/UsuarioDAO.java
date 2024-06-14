package modelo.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.entidades.Prestamo;
import modelo.entidades.Usuario;
import modelo.utils.FechaUtils;

public class UsuarioDAO implements Serializable {
    private final EntityManager entityManager;

    public UsuarioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Registra un nuevo usuario en la base de datos
    public void registrarUsuario(Usuario usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            ex.printStackTrace();
            // Manejo de la excepción
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    // Actualiza la información de un usuario en la base de datos
    public void actualizarUsuario(Usuario usuario) throws Exception {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw ex;
        }
    }

    // Elimina un usuario de la base de datos por su ID
    public void eliminarUsuario(Integer usuarioID) {
        entityManager.getTransaction().begin();
        try {
            Usuario usuario = entityManager.find(Usuario.class, usuarioID);
            if (usuario != null) {
                entityManager.remove(usuario);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("El usuario con ID " + usuarioID + " no existe.");
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    // Busca un usuario por su ID
    public Usuario buscarUsuario(Integer usuarioID) {
        return entityManager.find(Usuario.class, usuarioID);
    }
    
    // Obtiene un usuario por su ID
    public Usuario obtenerUsuarioPorID(Integer usuarioID) {
        return entityManager.find(Usuario.class, usuarioID);
    }

    // Marca un usuario como sancionado
    public void marcarUsuarioComoSancionado(Integer usuarioID) {
        Usuario usuario = entityManager.find(Usuario.class, usuarioID);
        if (usuario != null) {
            usuario.setSancionado(true);
            entityManager.merge(usuario);
        }
    }

    // Obtiene los préstamos de un usuario por su ID
    public List<Prestamo> obtenerPrestamosDeUsuario(Integer usuarioID) {
        Usuario usuario = entityManager.find(Usuario.class, usuarioID);
        if (usuario != null) {
            return (List<Prestamo>) usuario.getPrestamoCollection();
        }
        return null;
    }

    // Obtiene los préstamos pendientes de un usuario por su ID
    public List<Prestamo> obtenerPrestamosPendientesDeUsuario(Integer usuarioID) {
        Query query = entityManager.createQuery("SELECT p FROM Prestamo p WHERE p.usuarioID = :usuarioID AND p.devuelto = false");
        query.setParameter("usuarioID", usuarioID);
        return query.getResultList();
    }
    
    // Busca un usuario por su correo electrónico
    public Usuario buscarUsuarioPorCorreo(String correoElectronico) {
        TypedQuery<Usuario> query = entityManager.createNamedQuery("Usuario.findByEmail", Usuario.class);
        query.setParameter("correoElectronico", correoElectronico);
        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty()) {
            return usuarios.get(0); // Devuelve el primer usuario encontrado
        } else {
            return null; // No se encontró ningún usuario con ese correo electrónico
        }
    }
    
    // Valida un usuario por su correo electrónico y contraseña
    public Usuario validarUsuario(String email, String contrasena) {
        TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.correoElectronico = :email AND u.contrasena = :contrasena", Usuario.class);
        query.setParameter("email", email);
        query.setParameter("contrasena", contrasena);
        List<Usuario> usuarios = query.getResultList();

        if (!usuarios.isEmpty()) {
            return usuarios.get(0); // Devuelve el primer usuario encontrado
        } else {
            return null; // No se encontró ningún usuario con ese correo electrónico y contraseña
        }
    }

    // Busca empleados por tipo de usuario
    public List<Usuario> buscarEmpleados(String tipoUsuario1, String tipoUsuario2) {
        TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario1 OR u.tipoUsuario = :tipoUsuario2 ORDER BY u.tipoUsuario", Usuario.class);
        query.setParameter("tipoUsuario1", tipoUsuario1);
        query.setParameter("tipoUsuario2", tipoUsuario2);
        return query.getResultList();
    }

    // Busca socios no sancionados por tipo de usuario
    public List<Usuario> buscarSociosNoSancionados(String tipoUsuario) {
        return entityManager.createNamedQuery("Usuario.findSocioNoSancionados", Usuario.class)
                             .setParameter("tipoUsuario", tipoUsuario)
                             .getResultList();
    }

    // Busca socios con préstamos expirados
    public List<Usuario> buscarSociosConPrestamosExpirados() {
        Date fechaLimite = FechaUtils.calcularFechaHace30Dias();

        TypedQuery<Usuario> query = entityManager.createQuery(
                "SELECT DISTINCT p.usuarioID FROM Prestamo p " +
                        "WHERE p.fechaDevolucion IS NULL " +
                        "AND p.fechaPrestamo <= :fechaLimite " + 
                        "AND p.usuarioID.sancionado = false", Usuario.class);
        query.setParameter("fechaLimite", fechaLimite);

        // Aplicar refresh a la consulta
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        return query.getResultList();
    }
}
