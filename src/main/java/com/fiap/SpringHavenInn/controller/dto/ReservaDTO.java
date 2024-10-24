package com.fiap.SpringHavenInn.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class ReservaDTO {
    private Long id;

    @NotNull(message = "Data de Check-in é obrigatória.")
    private LocalDate dataCheckIn;

    @NotNull(message = "Data de Check-out é obrigatória.")
    private LocalDate dataCheckOut;

    private Long clienteId;

    private List<Long> quartosIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<Long> getQuartosIds() {
        return quartosIds;
    }

    public void setQuartosIds(List<Long> quartosIds) {
        this.quartosIds = quartosIds;
    }
}

