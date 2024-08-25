package com.thiago.promad.repository;

import com.thiago.promad.entity.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    @Query("SELECT p FROM Processo p JOIN p.numeros num WHERE num IN :numeros")
    Processo findByNumerosIn(@Param("numeros") List<Long> numeros);
}
