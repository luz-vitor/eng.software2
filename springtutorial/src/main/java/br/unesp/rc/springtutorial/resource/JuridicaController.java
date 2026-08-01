package br.unesp.rc.springtutorial.resource;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.rc.springtutorial.dto.JuridicaDTO;
import br.unesp.rc.springtutorial.dto.assembler.JuridicaAssembler;
import br.unesp.rc.springtutorial.entity.Juridica;
import br.unesp.rc.springtutorial.entity.mapper.JuridicaMapper;
import br.unesp.rc.springtutorial.service.JuridicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/pessoas-juridicas")
public class JuridicaController {

    private final JuridicaService juridicaService;

    public JuridicaController(JuridicaService juridicaService) {
        this.juridicaService = juridicaService;
    }

    @GetMapping("/")
    public List<Juridica> getAllJuridicas() {
        return juridicaService.findAll();
    }

    @Operation(summary = "Retorna uma pessoa pelo CNPJ", description = "Busca uma pessoa jurídica a partir do CNPJ informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa-jurídica encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Juridica.class))),
            @ApiResponse(responseCode = "400", description = "CNPJ inválido!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pessoa-jurídica não encontrada.", content = @Content)
    })
    @GetMapping("/{cnpj}")
    public Juridica getJuridicaByCnJuridica(@PathVariable(value = "cnpj") String cnpj) {
        Juridica juridica = juridicaService.findByCnpj(cnpj);

        return juridica;
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> delete(@PathVariable String cnpj) {
        Juridica juridica = juridicaService.findByCnpj(cnpj);

        if (juridica == null) {
            return ResponseEntity.notFound().build();
        }

        juridicaService.delete(juridica);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<JuridicaDTO> saveJuridica(@RequestBody JuridicaDTO juridicaDTO) {
        Juridica existente = juridicaService.findByCnpj(juridicaDTO.getCnpj());

        if (existente != null) {
            return ResponseEntity.badRequest().build();
        }

        Juridica juridica = JuridicaAssembler.dtoToEntityModel(juridicaDTO);
        juridica = juridicaService.save(juridica);

        return ResponseEntity.ok(JuridicaAssembler.entityToDtoModel(juridica));
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<JuridicaDTO> updateJuridica(@PathVariable String cnpj, @RequestBody JuridicaDTO juridicaDTO) {

        Juridica juridica = juridicaService.findByCnpj(cnpj);

        if (juridica == null) {
            return ResponseEntity.notFound().build();
        }

        Juridica novaJuridica = JuridicaAssembler.dtoToEntityModel(juridicaDTO);

        novaJuridica.setCnpj(juridica.getCnpj());

        JuridicaMapper.update(juridica, novaJuridica);

        juridica = juridicaService.save(juridica);

        return ResponseEntity.ok(JuridicaAssembler.entityToDtoModel(juridica));
    }
}
