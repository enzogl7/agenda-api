package com.ogl.agenda_api.controller;

import com.ogl.agenda_api.model.Contato;
import com.ogl.agenda_api.repository.ContatoRepository;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")
@RequiredArgsConstructor
@CrossOrigin("*")
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
    public Page<Contato> listar(@RequestParam(value = "page", defaultValue = "0") Integer pagina,
                                @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return contatoRepository.findAll(pageRequest);
    }

    @PatchMapping("/favoritar/{id}")
    public void favoritar(@PathVariable Integer id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        contato.ifPresent(ctt -> {
            boolean favorito = ctt.getFavorito() == Boolean.TRUE;
            ctt.setFavorito(!favorito);
            contatoRepository.save(ctt);
        });
    }

    @PutMapping("/add-foto/{id}")
    public byte[] addFoto(@PathVariable Integer id, @RequestParam("foto") Part arquivo) {
        Optional<Contato> contato = contatoRepository.findById(id);
        return contato.map(ctt -> {
            try {
                InputStream is = arquivo.getInputStream();
                byte[] bytes = new byte[Math.toIntExact(arquivo.getSize())];
                IOUtils.readFully(is, bytes);
                ctt.setFoto(bytes);
                contatoRepository.save(ctt);
                is.close();
                return bytes;
            } catch (IOException e) {
                return null;
            }
        }).orElse(null);

    }
}
