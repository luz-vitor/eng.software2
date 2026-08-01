package br.unesp.rc.springtutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.springtutorial.entity.Juridica;

public interface JuridicaRepository extends JpaRepository<Juridica, Long> {
    Juridica findByCnpj(String cnpj);

}
