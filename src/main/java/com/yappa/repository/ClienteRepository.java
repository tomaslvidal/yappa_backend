package com.yappa.repository;

import com.yappa.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    boolean existsByCuit(String cuit);
    
    boolean existsByEmail(String email);
    
    Optional<Cliente> findByCuit(String cuit);
    
    Optional<Cliente> findByEmail(String email);
}
