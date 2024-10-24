package com.fiap.SpringHavenInn.repository;

import com.fiap.SpringHavenInn.entity.Cliente;
import com.fiap.SpringHavenInn.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByClienteId(Long clienteId);

    List<Reserva> findByClienteAndDataCheckInBetween(Cliente cliente, LocalDate dataInicio, LocalDate dataFim);

    @Query("SELECT r FROM Reserva r WHERE r.cliente.id = ?1 AND r.dataCheckIn < ?3 AND r.dataCheckOut > ?2 AND r.hotel.id = ?4")
    Optional<Reserva> findConflictingReserva(Long clienteId, LocalDate dataCheckIn, LocalDate dataCheckOut, Long hotelId);

    List<Reserva> findByClienteIdAndAtivoTrue(Long clienteId);
}
