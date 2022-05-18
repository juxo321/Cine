package mx.uv.Cine;

import javax.persistence.*;


@Entity
@Table(name = "CompraBoleto", schema = "cine")
public class CompraBoleto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCompra", nullable = false)
    private int idCompra;
    private int noSala;
    private int cantidad;


    public CompraBoleto() {
    }

    public CompraBoleto(int idCompra, int noSala, int cantidad) {
        this.idCompra = idCompra;
        this.noSala = noSala;
        this.cantidad = cantidad;
    }


    public int getIdCompra() {
        return this.idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getNoSala() {
        return this.noSala;
    }

    public void setNoSala(int noSala) {
        this.noSala = noSala;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idFuncion", referencedColumnName = "idFuncion")
    private Funcion Funcion;


}
