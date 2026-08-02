package br.unesp.rc.springtutorial.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.messaging.PessoaEvent;
import br.unesp.rc.springtutorial.repository.FisicaRepository;

@Service
public class FisicaService {

    private final FisicaRepository repository;
    private final ApplicationEventPublisher eventos;

    public FisicaService(FisicaRepository repository,
            ApplicationEventPublisher eventos) {

        this.repository = repository;
        this.eventos = eventos;
    }

    @Transactional
    public Fisica save(Fisica entity) {

        boolean novo = entity.getIdPessoa() == 0;

        Fisica existente = repository.findByCpf(entity.getCpf());

        if (existente != null &&
                existente.getIdPessoa() != entity.getIdPessoa()) {

            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Fisica salvo = repository.save(entity);

        eventos.publishEvent(PessoaEvent.de(
                PessoaEvent.TipoPessoa.FISICA,
                novo ? PessoaEvent.Acao.CRIADA : PessoaEvent.Acao.ATUALIZADA,
                salvo.getCpf(),
                salvo.getNome()));

        return salvo;
    }

    public Fisica findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Transactional
    public void delete(Fisica entity) {

        repository.delete(entity);

        eventos.publishEvent(PessoaEvent.de(
                PessoaEvent.TipoPessoa.FISICA,
                PessoaEvent.Acao.REMOVIDA,
                entity.getCpf(),
                entity.getNome()));
    }

    @Transactional
    public Fisica update(Fisica entity) {

        Fisica persistido = repository.save(entity);

        eventos.publishEvent(PessoaEvent.de(
                PessoaEvent.TipoPessoa.FISICA,
                PessoaEvent.Acao.ATUALIZADA,
                persistido.getCpf(),
                persistido.getNome()));

        return persistido;
    }

    public List<Fisica> findAll() {
        return repository.findAll();
    }
}
