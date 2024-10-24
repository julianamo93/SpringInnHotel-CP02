package com.fiap.SpringHavenInn.controller;

import com.fiap.SpringHavenInn.controller.dto.ServicoDTO;
import com.fiap.SpringHavenInn.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> criarServico(@RequestBody ServicoDTO servicoDTO) {
        return new ResponseEntity<>(servicoService.criarServico(servicoDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarServicos() {
        return ResponseEntity.ok(servicoService.listarServicos());
    }
}
