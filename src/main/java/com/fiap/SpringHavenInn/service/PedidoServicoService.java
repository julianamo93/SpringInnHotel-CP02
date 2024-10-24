package com.fiap.SpringHavenInn.service;

import com.fiap.SpringHavenInn.controller.dto.PedidoServicoDTO;
import com.fiap.SpringHavenInn.entity.PedidoServico;
import com.fiap.SpringHavenInn.entity.Reserva;
import com.fiap.SpringHavenInn.entity.Servico;
import com.fiap.SpringHavenInn.exception.ResourceNotFoundException;
import com.fiap.SpringHavenInn.repository.PedidoServicoRepository;
import com.fiap.SpringHavenInn.repository.ReservaRepository;
import com.fiap.SpringHavenInn.repository.ServicoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PedidoServicoService {

    @Autowired
    private PedidoServicoRepository pedidoServicoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public PedidoServicoDTO criarPedidoServico(PedidoServicoDTO pedidoServicoDTO) {
        Set<ConstraintViolation<PedidoServicoDTO>> violations = validator.validate(pedidoServicoDTO);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<PedidoServicoDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }

        PedidoServico pedidoServico = new PedidoServico();
        pedidoServico.setDataPedido(pedidoServicoDTO.getDataPedido());

        // Você pode buscar a reserva e o serviço aqui
         Reserva reserva = reservaRepository.findById(pedidoServicoDTO.getReservaId())
             .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada."));
         pedidoServico.setReserva(reserva);

         Servico servico = servicoRepository.findById(pedidoServicoDTO.getServicoId())
             .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado."));
         pedidoServico.setServico(servico);

        return mapToDTO(pedidoServicoRepository.save(pedidoServico));
    }

    public List<PedidoServicoDTO> listarPedidosPorReserva(Long reservaId) {
        return pedidoServicoRepository.findByReservaId(reservaId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    private PedidoServicoDTO mapToDTO(PedidoServico pedidoServico) {
        PedidoServicoDTO dto = new PedidoServicoDTO();
        dto.setId(pedidoServico.getId());
        dto.setDataPedido(pedidoServico.getDataPedido());
        // Adicione os IDs da reserva e do serviço
        return dto;
    }
}
