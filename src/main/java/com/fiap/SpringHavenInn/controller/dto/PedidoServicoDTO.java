package com.fiap.SpringHavenInn.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;

public class PedidoServicoDTO {

    private Long id;

    @NotNull(message = "Data do pedido não pode ser nula.")
    @FutureOrPresent(message = "A data do pedido deve ser atual ou futura.")
    private LocalDateTime dataPedido;

    @NotNull(message = "A reserva não pode ser nula.")
    private Long reservaId;

    @NotNull(message = "O serviço não pode ser nulo.")
    private Long servicoId;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }
}

