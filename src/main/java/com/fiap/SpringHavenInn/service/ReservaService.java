package com.fiap.SpringHavenInn.service;

import com.fiap.SpringHavenInn.controller.dto.ReservaDTO;
import com.fiap.SpringHavenInn.entity.Cliente;
import com.fiap.SpringHavenInn.entity.Reserva;
import com.fiap.SpringHavenInn.exception.ResourceNotFoundException;
import com.fiap.SpringHavenInn.repository.ClienteRepository;
import com.fiap.SpringHavenInn.repository.ReservaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ReservaDTO criarReserva(ReservaDTO reservaDTO) {
        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(reservaDTO);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<ReservaDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }

        // Verificação de datas
        if (reservaDTO.getDataCheckOut().isBefore(reservaDTO.getDataCheckIn())) {
            throw new IllegalArgumentException("A data de Check-out deve ser posterior à data de Check-in.");
        }

        Reserva reserva = new Reserva();
        reserva.setDataCheckIn(reservaDTO.getDataCheckIn());
        reserva.setDataCheckOut(reservaDTO.getDataCheckOut());

        // Você pode buscar o cliente pelo ID e associá-lo aqui
         Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
             .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
         reserva.setCliente(cliente);

        // Você pode associar os quartos aqui

        return mapToDTO(reservaRepository.save(reserva));
    }

    public List<ReservaDTO> listarReservasPorCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void cancelarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva não encontrada.");
        }
        reservaRepository.deleteById(id);
    }

    private ReservaDTO mapToDTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setDataCheckIn(reserva.getDataCheckIn());
        dto.setDataCheckOut(reserva.getDataCheckOut());
        // Adicione os IDs do cliente e dos quartos
        return dto;
    }
}
