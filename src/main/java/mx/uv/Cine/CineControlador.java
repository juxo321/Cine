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
    
    @RequestMapping("/")
    public String home(){
        return "Hola mundo home(/)";
    }

    @PostMapping("/crearFuncion")
    public void crearFuncion(@RequestBody Funcion funcion){
        ifuncion.save(funcion);
    }

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



    //localhost:8080/cambiarFuncion?idCompra=22&idNuevaFuncion=7
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
