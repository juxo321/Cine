package mx.uv.Cine;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Funcion", schema = "cine")
public class Funcion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idFuncion", nullable = false)
    private int idFuncion;
    private String nombrePelicula;
    private String hora;
    private String fecha;
    private int precio;
    private String clasificacion;
    private int asientosDisponibles;


    public Funcion() {
    }

    public Funcion(int idFuncion, String nombrePelicula, String hora, String fecha, int precio, String clasificacion, int asientosDisponibles) {
        this.idFuncion = idFuncion;
        this.nombrePelicula = nombrePelicula;
        this.hora = hora;
        this.fecha = fecha;
        this.precio = precio;
        this.clasificacion = clasificacion;
        this.asientosDisponibles = asientosDisponibles;
    }


    public int getIdFuncion() {
        return this.idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public String getNombrePelicula() {
        return this.nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPrecio() {
        return this.precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getClasificacion() {
        return this.clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public int getAsientosDisponibles() {
        return this.asientosDisponibles;
    }

    public void setAsientosDisponibles(int asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    @OneToMany(mappedBy = "Funcion", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CompraBoleto> compraBoleto;



    public List<CompraBoleto> getCompraBoleto() {
        return this.compraBoleto;
    }

    public void setCompraBoleto(List<CompraBoleto> compraBoleto) {
        this.compraBoleto = compraBoleto;
    }


    @Override
    public String toString() {
        return "{" +
            " idFuncion='" + getIdFuncion() + "'" +
            ", nombrePelicula='" + getNombrePelicula() + "'" +
            ", hora='" + getHora() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", precio='" + getPrecio() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            ", asientosDisponibles='" + getAsientosDisponibles() + "'" +
            ", compraBoleto='" + getCompraBoleto() + "'" +
            "}";
    }



}
