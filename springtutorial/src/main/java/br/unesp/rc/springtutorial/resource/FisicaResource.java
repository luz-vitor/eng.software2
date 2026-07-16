package br.unesp.rc.springtutorial.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.rc.springtutorial.dto.FisicaDTO;
import br.unesp.rc.springtutorial.dto.assember.FisicaAssember;
import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.entity.mapper.FisicaMapper;
import br.unesp.rc.springtutorial.service.FisicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;




@RestController
@RequestMapping("/entidade/v1")
public class FisicaResource {
    
    @Autowired
    private FisicaService fisicaService;

    @GetMapping("/")
    public List<Fisica> getAllFisica(){
        return fisicaService.findAll();
    }

    @Operation(summary = "Retorna uma pessoa pelo CPF")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Pessoa-física encontrada!",
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = Fisica.class))
            ),
            
            @ApiResponse(responseCode = "400", description = "CPF inválido!", content = @Content),

            @ApiResponse(responseCode = "404", description = "Pessoa-física não encontrada!", content = @Content)
        }
    )

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

    @PostMapping("/")
    public boolean saveFisica(@RequestBody FisicaDTO fisicaDTO) {
        boolean insert = false;

        Fisica fisica = FisicaAssember.dtoToEntityModel(fisicaDTO);
        Fisica fisicaInsert = fisicaService.save(fisica);
        if(fisicaInsert != null){
            insert = true;
        }

        return insert;
    }
    
    @PutMapping("/")
    public boolean update(@RequestBody FisicaDTO fisicaDTO){
        boolean update = false;

        Fisica newFisica = FisicaAssember.dtoToEntityModel(fisicaDTO);
        Fisica fisicaUpdate = fisicaService.findByCpf(newFisica.getCpf());

        FisicaMapper.update(fisicaUpdate, newFisica);

        Fisica fisicaUpdated = fisicaService.update(fisicaUpdate);

        if(fisicaUpdated != null){
            update = true;
        }

        return update;
    }

}
