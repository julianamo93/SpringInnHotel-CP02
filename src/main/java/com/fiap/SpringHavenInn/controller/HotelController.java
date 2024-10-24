package com.fiap.SpringHavenInn.controller;

import com.fiap.SpringHavenInn.controller.dto.HotelDTO;
import com.fiap.SpringHavenInn.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoteis")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> criarHotel(@RequestBody HotelDTO hotelDTO) {
        return new ResponseEntity<>(hotelService.criarHotel(hotelDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> listarHoteis() {
        return ResponseEntity.ok(hotelService.listarHoteis());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirHotel(@PathVariable Long id) {
        hotelService.excluirHotel(id);
        return ResponseEntity.noContent().build();
    }
}
