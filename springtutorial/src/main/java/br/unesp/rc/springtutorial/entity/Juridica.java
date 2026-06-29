package br.unesp.rc.springtutorial.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true, includeFieldNames = true)

public class Juridica extends Pessoa{
    private String cnpj;

    public Juridica(){
        
    }

}
