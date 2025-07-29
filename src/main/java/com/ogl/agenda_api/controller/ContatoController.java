package com.ogl.agenda_api.controller;

import com.ogl.agenda_api.model.Contato;
import com.ogl.agenda_api.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoRepository contatoRepository;

    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public Contato salvar(@RequestBody Contato contato) {
        return contatoRepository.save(contato);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        contatoRepository.deleteById(id);
    }

    @GetMapping("/listar")
    public List<Contato> listar() {
        return contatoRepository.findAll();
    }

    @PatchMapping("/favoritar/{id}")
    public void favoritar(@PathVariable Integer id, @RequestBody Boolean favorito) {
        Optional<Contato> contato = contatoRepository.findById(id);
        contato.ifPresent(ctt -> {
            ctt.setFavorito(favorito);
            contatoRepository.save(ctt);
        });
    }
}
