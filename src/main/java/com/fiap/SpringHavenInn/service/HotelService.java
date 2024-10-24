package com.fiap.SpringHavenInn.service;

import com.fiap.SpringHavenInn.controller.dto.HotelDTO;
import com.fiap.SpringHavenInn.entity.Hotel;
import com.fiap.SpringHavenInn.exception.ResourceNotFoundException;
import com.fiap.SpringHavenInn.repository.HotelRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public HotelDTO criarHotel(HotelDTO hotelDTO) {
        Set<ConstraintViolation<HotelDTO>> violations = validator.validate(hotelDTO);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<HotelDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }

        Hotel hotel = new Hotel();
        hotel.setNome(hotelDTO.getNome());
        hotel.setEndereco(hotelDTO.getEndereco());

        return mapToDTO(hotelRepository.save(hotel));
    }

    public List<HotelDTO> listarHoteis() {
        return hotelRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public void excluirHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel não encontrado.");
        }
        hotelRepository.deleteById(id);
    }

    private HotelDTO mapToDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setNome(hotel.getNome());
        dto.setEndereco(hotel.getEndereco());
        return dto;
    }
}


