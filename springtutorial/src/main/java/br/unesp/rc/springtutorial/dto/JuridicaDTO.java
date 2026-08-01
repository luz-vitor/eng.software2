package br.unesp.rc.springtutorial.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JuridicaDTO {

    private String usuario;
    private String senha;
    private String telefoneComercial;
    private String celular;
    private String email;
    private String nome;
    private String cnpj;
    private List<EnderecoDTO> endereco;

    public JuridicaDTO() {
    }

    public JuridicaDTO(String usuario, String senha,
            String telefoneComercial, String celular,
            String email, String nome,
            String cnpj, List<EnderecoDTO> endereco) {
        this.usuario = usuario;
        this.senha = senha;
        this.telefoneComercial = telefoneComercial;
        this.celular = celular;
        this.email = email;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }
}
