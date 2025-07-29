package com.ogl.agenda_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 120, nullable = false)
    private String nome;
    @Column(length = 150, nullable = false)
    private String email;
    @Column
    private Boolean favorito;
    @Column
    @Lob
    private byte[] foto;
}
