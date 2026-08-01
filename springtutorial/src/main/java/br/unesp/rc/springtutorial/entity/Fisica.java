package br.unesp.rc.springtutorial.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PessoaFisica")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = { "dataNascimento" })
@ToString(callSuper = true, includeFieldNames = true)

public class Fisica extends Pessoa {
    private static final long serialVersionUID = 1L;

    @Column(name = "cpf", unique = true)
    @NotBlank
    @Size(min = 11, max = 14)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    public Fisica() {

    }

}
