package com.fiap.SpringHavenInn.service;

import com.fiap.SpringHavenInn.controller.dto.ServicoDTO;
import com.fiap.SpringHavenInn.entity.Servico;
import com.fiap.SpringHavenInn.repository.ServicoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ServicoDTO criarServico(ServicoDTO servicoDTO) {
        Set<ConstraintViolation<ServicoDTO>> violations = validator.validate(servicoDTO);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<ServicoDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }

        Servico servico = new Servico();
        servico.setDescricao(servicoDTO.getDescricao());
        servico.setPreco(servicoDTO.getPreco());

        return mapToDTO(servicoRepository.save(servico));
    }

    public List<ServicoDTO> listarServicos() {
        return servicoRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    private ServicoDTO mapToDTO(Servico servico) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(servico.getId());
        dto.setDescricao(servico.getDescricao());
        dto.setPreco(servico.getPreco());
        return dto;
    }
}

