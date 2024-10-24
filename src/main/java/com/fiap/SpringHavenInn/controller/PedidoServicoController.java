package com.fiap.SpringHavenInn.controller;

import com.fiap.SpringHavenInn.entity.PedidoServico;
import com.fiap.SpringHavenInn.entity.Reserva;
import com.fiap.SpringHavenInn.repository.PedidoServicoRepository;
import com.fiap.SpringHavenInn.repository.ReservaRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos-servico")
public class PedidoServicoController {

    @Autowired
    private PedidoServicoRepository pedidoServicoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @PostMapping
    public ResponseEntity<?> criarPedidoServico(@Valid @RequestBody PedidoServico pedidoServico) {
        Optional<Reserva> reserva = reservaRepository.findById(pedidoServico.getReserva().getId());
        if (reserva.isPresent() && reserva.get().getDataCheckOut().isAfter(LocalDate.now())) {
            PedidoServico novoPedidoServico = pedidoServicoRepository.save(pedidoServico);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedidoServico);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Pedidos de serviço só podem ser realizados para reservas ativas.");
    }

    @GetMapping
    public ResponseEntity<List<PedidoServico>> listarPedidosPorReserva(@RequestParam Long reservaId) {
        Optional<Reserva> reserva = reservaRepository.findById(reservaId);
        if (reserva.isPresent()) {
            List<PedidoServico> pedidos = pedidoServicoRepository.findByReserva(reserva.get());
            return ResponseEntity.ok(pedidos);
        }
        return ResponseEntity.notFound().build();
    }
}

