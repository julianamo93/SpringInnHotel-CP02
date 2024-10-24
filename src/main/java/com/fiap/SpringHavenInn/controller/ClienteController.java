package com.fiap.SpringHavenInn.controller;

import com.fiap.SpringHavenInn.entity.Cliente;
import com.fiap.SpringHavenInn.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) {
        Cliente novoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(@RequestParam(required = false) String nome) {
        List<Cliente> clientes = (nome == null)
                ? clienteRepository.findAll()
                : clienteRepository.findByNomeContaining(nome);
        return ResponseEntity.ok(clientes);
    }
}

