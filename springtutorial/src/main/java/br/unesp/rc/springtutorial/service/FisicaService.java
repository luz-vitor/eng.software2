package br.unesp.rc.springtutorial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.repository.FisicaRepository;

@Component
public class FisicaService {

    @Autowired
    private FisicaRepository repository;

    public FisicaService() {

    }

    public Fisica save(Fisica entity) {
        Fisica existente = repository.findByCpf(entity.getCpf());
        if (existente != null &&
                existente.getIdPessoa() != entity.getIdPessoa()) {

            throw new IllegalArgumentException("CPF já cadastrado");
        }

        return repository.save(entity);
    }

    public Fisica findByCpf(String cpf) {
        Fisica insertedEntity = null;

        if (repository != null) {
            insertedEntity = repository.findByCpf(cpf);
        }

        return insertedEntity;
    }

    public void delete(Fisica entity) {

        if (repository != null) {
            repository.delete(entity);
        }
    }

    public Fisica update(Fisica entity) {
        Fisica persistedEntity = null;

        if (repository != null) {
            persistedEntity = repository.save(entity);
        }

        return persistedEntity;
    }

    // public List<Fisica> findAll(){
    // List<Fisica> list = null;

    // if(repository != null){
    // list = new ArrayList<>();
    // list = repository.findAll();
    // }

    // return list;
    // }
    public List<Fisica> findAll() {
        return repository.findAll();
    }
}
