package com.thiago.promad.repository;

import com.thiago.promad.entity.Reu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReuRepository extends JpaRepository<Reu, Long> {
    Optional<Reu> findByNome(String nome);
}
