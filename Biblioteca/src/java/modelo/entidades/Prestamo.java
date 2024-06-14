package modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "prestamo")
@NamedQueries({
    @NamedQuery(name = "Prestamo.findAll", query = "SELECT p FROM Prestamo p ORDER BY p.fechaPrestamo DESC"),
    @NamedQuery(name = "Prestamo.findByLibroISBN", query = "SELECT p FROM Prestamo p WHERE p.libroISBN = :libroISBN")})
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PrestamoID")
    private Integer prestamoID;
    @Basic(optional = false)
    @Column(name = "FechaPrestamo")
    @Temporal(TemporalType.DATE)
    private Date fechaPrestamo;
    @Basic(optional = true)
    @Column(name = "FechaDevolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @JoinColumn(name = "LibroISBN", referencedColumnName = "ISBN")
    @ManyToOne(optional = false)
    private Libro libroISBN;
    @JoinColumn(name = "UsuarioID", referencedColumnName = "UsuarioID")
    @ManyToOne(optional = false)
    private Usuario usuarioID;

    public Prestamo() {
    }

    public Prestamo(Integer prestamoID) {
        this.prestamoID = prestamoID;
    }

    public Prestamo(Date fechaPrestamo, Libro libroISBN, Usuario usuarioID) {
        this.fechaPrestamo = fechaPrestamo;
        this.libroISBN = libroISBN;
        this.usuarioID = usuarioID;
    }

    public Integer getPrestamoID() {
        return prestamoID;
    }

    public void setPrestamoID(Integer prestamoID) {
        this.prestamoID = prestamoID;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Libro getLibroISBN() {
        return libroISBN;
    }

    public void setLibroISBN(Libro libroISBN) {
        this.libroISBN = libroISBN;
    }

    public Usuario getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Usuario usuarioID) {
        this.usuarioID = usuarioID;
    }
    
    public int calcularDiasTranscurridos(Date fechaPrestamo, Date fechaDevolucion) {
        Date fechaActual = new Date();

        if (fechaDevolucion == null) {
            // Si la fecha de devolución es null, calculamos los días desde la entrega hasta hoy
            long diferenciaMillis = fechaActual.getTime() - fechaPrestamo.getTime();
            return (int) (diferenciaMillis / (1000 * 60 * 60 * 24)); // Convertimos milisegundos a días
        } else {
            // Si la fecha de devolución está presente, calculamos los días entre entrega y devolución
            long diferenciaMillis = fechaDevolucion.getTime() - fechaPrestamo.getTime();
            return (int) (diferenciaMillis / (1000 * 60 * 60 * 24)); // Convertimos milisegundos a días
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prestamoID != null ? prestamoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.prestamoID == null && other.prestamoID != null) || (this.prestamoID != null && !this.prestamoID.equals(other.prestamoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Prestamo[ prestamoID=" + prestamoID + " ]";
    }
    
}
