package com.fiap.SpringHavenInn.service;

import com.fiap.SpringHavenInn.controller.dto.QuartoDTO;
import com.fiap.SpringHavenInn.entity.Hotel;
import com.fiap.SpringHavenInn.entity.Quarto;
import com.fiap.SpringHavenInn.exception.ResourceNotFoundException;
import com.fiap.SpringHavenInn.repository.HotelRepository;
import com.fiap.SpringHavenInn.repository.QuartoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuartoService {

    private final QuartoRepository quartoRepository;
    private final HotelRepository hotelRepository;

    public QuartoService(QuartoRepository quartoRepository, HotelRepository hotelRepository) {
        this.quartoRepository = quartoRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Quarto> getQuartosByHotelAndTipo(Long hotelId, String tipo) {
        if (hotelId != null && tipo != null) {
            return quartoRepository.findByHotelIdAndTipo(hotelId, tipo);
        } else if (hotelId != null) {
            return quartoRepository.findByHotelId(hotelId);
        } else {
            return quartoRepository.findAll();
        }
    }

    public List<Quarto> getQuartosDisponiveis(LocalDate dataEntrada, LocalDate dataSaida) {
        return quartoRepository.findAvailableRooms(dataEntrada, dataSaida);
    }

    public List<Quarto> getQuartos() {
        return quartoRepository.findAll();
    }

    @Transactional
    public ResponseEntity<String> createQuarto(QuartoDTO quartoDTO, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com o ID: " + hotelId));

        Quarto quarto = new Quarto();
        quarto.setNumero(quartoDTO.getNumero());
        quarto.setTipo(quartoDTO.getTipo());
        quarto.setHotel(hotel);

        quartoRepository.save(quarto);
        return ResponseEntity.status(201).body("Quarto criado com sucesso.");
    }

}
