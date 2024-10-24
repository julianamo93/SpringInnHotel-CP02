package com.fiap.SpringHavenInn.repository;

import com.fiap.SpringHavenInn.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Possível pesquisa por nome ou documento
    List<Cliente> findByNomeContaining(String nome);
}
