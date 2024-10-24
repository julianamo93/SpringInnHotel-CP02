package com.fiap.SpringHavenInn.repository;

import com.fiap.SpringHavenInn.entity.PedidoServico;
import com.fiap.SpringHavenInn.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoServicoRepository extends JpaRepository<PedidoServico, Long> {
    List<PedidoServico> findByReservaId(Long reservaId);

    List<PedidoServico> findByReserva(Reserva reserva);
}

