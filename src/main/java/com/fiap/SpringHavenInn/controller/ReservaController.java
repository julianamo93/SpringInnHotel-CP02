package com.fiap.SpringHavenInn.controller;

import com.fiap.SpringHavenInn.entity.Cliente;
import com.fiap.SpringHavenInn.entity.Reserva;
import com.fiap.SpringHavenInn.repository.ClienteRepository;
import com.fiap.SpringHavenInn.repository.QuartoRepository;
import com.fiap.SpringHavenInn.repository.ReservaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<?> criarReserva(@Valid @RequestBody Reserva reserva) {
        // Verifica se já existe uma reserva para o mesmo período e hotel
        Optional<Reserva> reservaExistente = reservaRepository.findConflictingReserva(
                reserva.getCliente().getId(),
                reserva.getDataCheckIn(),
                reserva.getDataCheckOut(),
                reserva.getQuartos().get(0).getHotel().getId()
        );

        if (reservaExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O cliente já possui uma reserva para esse período neste hotel.");
        }

        // Salvar reserva
        Reserva novaReserva = reservaRepository.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservasPorClienteEPeriodo(@RequestParam Long clienteId,
                                                                          @RequestParam LocalDate dataInicio,
                                                                          @RequestParam LocalDate dataFim) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            List<Reserva> reservas = reservaRepository.findByClienteAndDataCheckInBetween(
                    cliente.get(), dataInicio, dataFim);
            return ResponseEntity.ok(reservas);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            reservaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

