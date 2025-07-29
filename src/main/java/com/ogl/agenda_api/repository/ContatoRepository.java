package com.ogl.agenda_api.repository;

import com.ogl.agenda_api.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
}
