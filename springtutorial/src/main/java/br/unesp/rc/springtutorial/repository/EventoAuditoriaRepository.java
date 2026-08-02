package br.unesp.rc.springtutorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.springtutorial.entity.EventoAuditoria;

public interface EventoAuditoriaRepository extends JpaRepository<EventoAuditoria, Long> {

    List<EventoAuditoria> findAllByOrderByOcorridoEmDesc();
}
