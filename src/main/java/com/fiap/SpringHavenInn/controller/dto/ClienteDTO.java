package com.fiap.SpringHavenInn.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String email;

    private String documento;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
