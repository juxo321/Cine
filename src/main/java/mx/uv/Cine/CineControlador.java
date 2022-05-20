package mx.uv.Cine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RestController
public class CineControlador {

    @Autowired
    Ifuncion ifuncion;

    @Autowired
    IcompraBoleto icompraBoleto;
    
    //Para poder hacer consumo del recurso de este microservicio (Debido a que es un metodo post se tiene que hacer uso de una version especifica de curl que permita el envio de payload)
    //Otra opcion para consumir este recurso es hacer uso de un cliente con interfaz gráfica para hacer peticiones por ejemplo con Postman:

    //En el apartado de la Request URL se pondria lo siguiente:
    //https://microservicio-funcion.herokuapp.com/crearFuncion

    // En el envio de parametros seria un JSON:
    //{"nombrePelicula":"El Libro De La Selva","hora":"10:45","fecha":"12/02/2022","precio":55,"clasificacion":"A","asientosDisponibles":15}
    @PostMapping("/crearFuncion")
    public void crearFuncion(@RequestBody Funcion funcion){
        ifuncion.save(funcion);
    }


    //Para el recurso de comprar un boleto para alguna funcion se hace uso de un envio post pero también se necesita pasar un parametro por la URI:
    //https://microservicio-funcion.herokuapp.com/comprarBoletos?idFuncion=1    (Se pasa el id de la funcion de la cual se quiere comprar boletos)

    // En el envio de parametros seria un JSON donde se indica la cantidad de boletos para comprar:
    //{"cantidad":3}

    @PostMapping("/comprarBoletos")
    public boolean comprarBoletos(@RequestBody CompraBoleto compraBoletoRecibido , @RequestParam int idFuncion){
        CompraBoleto compraBoleto = compraBoletoRecibido;
        Optional<Funcion> funcionOptional = ifuncion.findById(idFuncion);
        Funcion funcion = funcionOptional.get();
        if(funcion.getAsientosDisponibles()<compraBoletoRecibido.getCantidad()){
            return false;
        }else{
            compraBoleto.setCantidad(compraBoletoRecibido.getCantidad());
            compraBoleto.setFuncion(funcion);
            compraBoleto.setTotal(compraBoletoRecibido.getCantidad() * funcion.getPrecio());
            funcion.setAsientosDisponibles(funcion.getAsientosDisponibles()-compraBoletoRecibido.getCantidad());
            icompraBoleto.save(compraBoleto);
            ifuncion.save(funcion);
            return true;
        }
        
    }

    //Pra este recurso solamente en necesario consumirlo con la siguiente URL:
    //https://microservicio-funcion.herokuapp.com/verCartelera
    @GetMapping("/verCartelera")
    public List<Funcion> verCartelera(){
        List<Funcion> cartelera = new ArrayList<>();
        Iterable<Funcion> carteleraIterable = ifuncion.findAll();
        for (Funcion funcion : carteleraIterable) {
            if(funcion.getAsientosDisponibles()>0){
                cartelera.add(funcion);
            }
            
        }
        return cartelera;
    }



    //Para consumir este recurso no es necesario hacer uso de un archivo JSON aunque sea PUT ya que lo que requerimos son parametros, el id de la compra de los boletos
    // y la nueva funcion a la que se desea cambair. 
    //localhost:8080/cambiarFuncion?idCompra=2&idNuevaFuncion=7
    @PutMapping("/cambiarFuncion")
    public String cambiarFuncion(@RequestParam(name="idCompra") int idCompra , @RequestParam(name = "idNuevaFuncion") int idNuevaFuncion){
        Optional<CompraBoleto> compraBoletoOptional = icompraBoleto.findById(idCompra);
        CompraBoleto compraBoleto = compraBoletoOptional.get();
        Optional<Funcion> funcionOptional2 = ifuncion.findById(idNuevaFuncion);
        Funcion funcionNueva = funcionOptional2.get();
        if(funcionNueva.getAsientosDisponibles()>=compraBoleto.getCantidad()){
            Funcion funcionAntigua = compraBoleto.getFuncion();
            funcionAntigua.setAsientosDisponibles(funcionAntigua.getAsientosDisponibles()+compraBoleto.getCantidad());
            funcionNueva.setAsientosDisponibles(funcionAntigua.getAsientosDisponibles()-compraBoleto.getCantidad());
            compraBoleto.setFuncion(funcionNueva);
            icompraBoleto.save(compraBoleto);
            ifuncion.save(funcionNueva);
            ifuncion.save(funcionAntigua);
            return "Funcion cambiada exitosamente";
        }else{
            return "No hay suficientes acientos para esta función";
        } 
    }




    

}
