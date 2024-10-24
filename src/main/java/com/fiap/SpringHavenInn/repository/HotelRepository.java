package com.fiap.SpringHavenInn.repository;

import com.fiap.SpringHavenInn.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNomeContaining(String nome, Pageable pageable);
}
