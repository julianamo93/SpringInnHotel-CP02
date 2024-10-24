package com.fiap.SpringHavenInn.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class HotelDTO {
    private Long id;

    @NotBlank(message = "O nome do hotel é obrigatório.")
    private String nome;

    @NotBlank(message = "O endereço do hotel é obrigatório.")
    private String endereco;

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}

