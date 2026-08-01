package br.unesp.rc.springtutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.unesp.rc.springtutorial.entity.Juridica;
import br.unesp.rc.springtutorial.repository.JuridicaRepository;

@Service
public class JuridicaService {

    private final JuridicaRepository repository;

    public JuridicaService(JuridicaRepository repository) {
        this.repository = repository;
    }

    public Juridica save(Juridica entity) {
        Juridica existente = repository.findByCnpj(entity.getCnpj());

        if (existente != null &&
                existente.getIdPessoa() != entity.getIdPessoa()) {

            throw new IllegalArgumentException("CNPJ já cadastrado");
        }

        return repository.save(entity);
    }

    public Juridica findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }

    public void delete(Juridica juridica) {
        if (juridica == null || juridica.getIdPessoa() == 0 || !repository.existsById(juridica.getIdPessoa())) {
            throw new RuntimeException("Não é possível deletar: Empresa não encontrada.");
        }

        repository.deleteById(juridica.getIdPessoa());
    }

    public Juridica update(Juridica entity) {
        if (entity == null || entity.getIdPessoa() == 0 || !repository.existsById(entity.getIdPessoa())) {
            throw new RuntimeException("Não é possível atualizar: Empresa não encontrada.");
        }

        return repository.save(entity);
    }

    public List<Juridica> findAll() {
        return repository.findAll();
    }

}
