package com.fiap.SpringHavenInn.service;

import com.fiap.SpringHavenInn.controller.dto.ClienteDTO;
import com.fiap.SpringHavenInn.entity.Cliente;
import com.fiap.SpringHavenInn.exception.ResourceNotFoundException;
import com.fiap.SpringHavenInn.repository.ClienteRepository;
import com.fiap.SpringHavenInn.repository.ReservaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Service
@Validated
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        // Validação do ClienteDTO
        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<ClienteDTO> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException("Validation failed: " + sb.toString());
        }

        // Criação do cliente
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setDocumento(clienteDTO.getDocumento());

        // Salva o cliente e retorna o DTO atualizado
        Cliente savedCliente = clienteRepository.save(cliente);
        clienteDTO.setId(savedCliente.getId());
        return clienteDTO;
    }

    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(this::convertToDTO);
    }

    public ClienteDTO buscarPorId(Long id) {
        // Busca o cliente pelo ID
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        return convertToDTO(cliente);
    }

    public void deletarCliente(Long id) {
        // Verifica se o cliente possui reservas ativas
        if (clienteTemReservasAtivas(id)) {
            throw new IllegalArgumentException("Cliente não pode ser excluído pois possui reservas ativas.");
        }
        clienteRepository.deleteById(id);
    }

    private boolean clienteTemReservasAtivas(Long clienteId) {
        // Verifica se o cliente possui reservas ativas
        return !reservaRepository.findByClienteIdAndAtivoTrue(clienteId).isEmpty();
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        // Converte entidade Cliente em DTO
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setDocumento(cliente.getDocumento());
        return dto;
    }
}
