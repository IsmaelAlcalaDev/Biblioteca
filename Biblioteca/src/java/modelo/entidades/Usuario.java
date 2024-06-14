package modelo.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findSocioNoSancionados", query = "SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario AND u.sancionado = false"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.correoElectronico = :correoElectronico"),})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UsuarioID")
    private Integer usuarioID;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "CorreoElectronico")
    private String correoElectronico;
    @Basic(optional = false)
    @Column(name = "Contrasena")
    private String contrasena;
    @Basic(optional = false)
    @Column(name = "Domicilio")
    private String domicilio;
    @Basic(optional = false)
    @Column(name = "TipoUsuario")
    private String tipoUsuario;
    @Column(name = "Sancionado")
    private Boolean sancionado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioID")
    private Collection<Prestamo> prestamoCollection;

    public Usuario() {
    }
    
    // Constructor sin el campo usuarioID
    public Usuario(String nombre, String apellidos, String correoElectronico, String contrasena, String domicilio, String tipoUsuario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.tipoUsuario = tipoUsuario;
    }

    // Constructor con todos los campos
    public Usuario(Integer usuarioID, String nombre, String apellidos, String correoElectronico, String contrasena, String domicilio, String tipoUsuario, Boolean sancionado) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.tipoUsuario = tipoUsuario;
        this.sancionado = sancionado;
    }

    public Usuario(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Boolean getSancionado() {
        return sancionado;
    }

    public void setSancionado(Boolean sancionado) {
        this.sancionado = sancionado;
    }

    public Collection<Prestamo> getPrestamoCollection() {
        return prestamoCollection;
    }

    public void setPrestamoCollection(Collection<Prestamo> prestamoCollection) {
        this.prestamoCollection = prestamoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioID != null ? usuarioID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioID == null && other.usuarioID != null) || (this.usuarioID != null && !this.usuarioID.equals(other.usuarioID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Usuario[ usuarioID=" + usuarioID + " ]";
    }
    
}
