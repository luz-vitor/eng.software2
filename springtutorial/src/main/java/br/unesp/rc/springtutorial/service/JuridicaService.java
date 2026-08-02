package br.unesp.rc.springtutorial.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.unesp.rc.springtutorial.entity.Juridica;
import br.unesp.rc.springtutorial.messaging.PessoaEvent;
import br.unesp.rc.springtutorial.repository.JuridicaRepository;

@Service
public class JuridicaService {

    private final JuridicaRepository repository;
    private final ApplicationEventPublisher eventos;

    public JuridicaService(JuridicaRepository repository,
            ApplicationEventPublisher eventos) {

        this.repository = repository;
        this.eventos = eventos;
    }

    @Transactional
    public Juridica save(Juridica entity) {

        boolean novo = entity.getIdPessoa() == 0;

        Juridica existente = repository.findByCnpj(entity.getCnpj());

        if (existente != null &&
                existente.getIdPessoa() != entity.getIdPessoa()) {

            throw new IllegalArgumentException("CNPJ já cadastrado");
        }

        Juridica salvo = repository.save(entity);

        eventos.publishEvent(PessoaEvent.de(
                PessoaEvent.TipoPessoa.JURIDICA,
                novo ? PessoaEvent.Acao.CRIADA : PessoaEvent.Acao.ATUALIZADA,
                salvo.getCnpj(),
                salvo.getNome()));

        return salvo;
    }

    public Juridica findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }

    @Transactional
    public void delete(Juridica juridica) {

        if (juridica == null ||
                juridica.getIdPessoa() == 0 ||
                !repository.existsById(juridica.getIdPessoa())) {

            throw new RuntimeException("Não é possível deletar: Empresa não encontrada.");
        }

        repository.deleteById(juridica.getIdPessoa());

        eventos.publishEvent(PessoaEvent.de(
                PessoaEvent.TipoPessoa.JURIDICA,
                PessoaEvent.Acao.REMOVIDA,
                juridica.getCnpj(),
                juridica.getNome()));
    }

    @Transactional
    public Juridica update(Juridica entity) {

        if (entity == null ||
                entity.getIdPessoa() == 0 ||
                !repository.existsById(entity.getIdPessoa())) {

            throw new RuntimeException("Não é possível atualizar: Empresa não encontrada.");
        }

        Juridica salvo = repository.save(entity);

        eventos.publishEvent(PessoaEvent.de(
                PessoaEvent.TipoPessoa.JURIDICA,
                PessoaEvent.Acao.ATUALIZADA,
                salvo.getCnpj(),
                salvo.getNome()));

        return salvo;
    }

    public List<Juridica> findAll() {
        return repository.findAll();
    }
}
