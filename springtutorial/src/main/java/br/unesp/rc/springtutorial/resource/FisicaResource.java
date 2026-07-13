package br.unesp.rc.springtutorial.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.service.FisicaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/entidade/v1")
public class FisicaResource {
    
    @Autowired
    private FisicaService fisicaService;

    @GetMapping("/")
    public List<Fisica> getAllFisica(){
        return fisicaService.findAll();
    }

    @GetMapping("/{cpf}")
    public Fisica getFisicaByCpFisica(@PathVariable(value = "cpf") String cpf) {
        Fisica fisica = fisicaService.findByCpf(cpf);
        
        return fisica;
    }

    @DeleteMapping("/{cpf}")
    public boolean delete(@PathVariable(value = "cpf") String cpf){
        boolean delete = false;

        Fisica fisicaDelete = fisicaService.findByCpf(cpf);

        if(fisicaDelete != null){
            fisicaService.delete(fisicaDelete);
            delete = true;
        }

        return delete;
    }


}
