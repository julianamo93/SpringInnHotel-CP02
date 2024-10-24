package com.fiap.SpringHavenInn.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class QuartoDTO {
    private Long id;

    @NotBlank(message = "O número do quarto é obrigatório.")
    private String numero;

    @NotBlank(message = "O tipo de quarto é obrigatório.")
    private String tipo;

    private Long hotelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}
