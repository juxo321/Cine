package mx.uv.Cine;

import javax.persistence.*;


@Entity
@Table(name = "CompraBoleto", schema = "cine")
public class CompraBoleto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCompra", nullable = false)
    private int idCompra;
    private int cantidad;
    private int total;


    public CompraBoleto() {
    }

    public CompraBoleto(int idCompra, int cantidad) {
        this.idCompra = idCompra;
        this.cantidad = cantidad;
    }


    public CompraBoleto(int cantidad) {
        this.cantidad = cantidad;
    }
    


    public int getIdCompra() {
        return this.idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idFuncion", referencedColumnName = "idFuncion")
    private Funcion Funcion;


    public Funcion getFuncion() {
        return this.Funcion;
    }

    public void setFuncion(Funcion Funcion) {
        this.Funcion = Funcion;
    }


}
