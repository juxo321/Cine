package mx.uv.Cine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;



@RestController
public class CineControlador {

    private static Gson gson = new Gson();
    
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
        Funcion funcion2 = funcion;
        System.out.println(funcion2.toString());

    }

    

}
