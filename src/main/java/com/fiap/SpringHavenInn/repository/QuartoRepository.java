package com.fiap.SpringHavenInn.repository;

import com.fiap.SpringHavenInn.entity.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {


    List<Quarto> findByHotelId(Long hotelId);

    List<Quarto> findByHotelIdAndTipo(Long hotelId, String tipo);

    @Query("SELECT q FROM Quarto q WHERE q NOT IN " +
            "(SELECT r.quartos FROM Reserva r WHERE " +
            "(r.dataCheckIn <= :dataSaida AND r.dataCheckOut >= :dataEntrada))")
    List<Quarto> findAvailableRooms(@Param("dataEntrada") LocalDate dataEntrada,
                                    @Param("dataSaida") LocalDate dataSaida);

}
