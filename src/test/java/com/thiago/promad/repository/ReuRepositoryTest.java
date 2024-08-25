package com.thiago.promad.repository;

import com.thiago.promad.entity.Reu;
import com.thiago.promad.requests.ReuRequestBody;
import com.thiago.promad.util.ReuRequestBodyCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReuRepositoryTest {

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateReu() {
        ReuRequestBody reuRequestBody = ReuRequestBodyCreator.createReuRequestBody();
        Reu reu = new Reu();
        reu.setNome(reuRequestBody.getNome());
        Reu savedReu = reuRepository.save(reu);

        assertThat(savedReu).isNotNull();
        assertThat(savedReu.getNome()).isEqualTo(reuRequestBody.getNome());
    }

    @Test
    public void testFindByNome() {
        ReuRequestBody reuRequestBody = ReuRequestBodyCreator.createReuRequestBody();
        Reu reu = new Reu();
        reu.setNome(reuRequestBody.getNome());
        testEntityManager.persistAndFlush(reu);

        Optional<Reu> foundReu = reuRepository.findByNome(reuRequestBody.getNome());

        assertThat(foundReu).isPresent();
        assertThat(foundReu.get().getNome()).isEqualTo(reuRequestBody.getNome());
    }

    @Test
    public void testDeleteReu() {
        ReuRequestBody reuRequestBody = ReuRequestBodyCreator.createReuRequestBody();
        Reu reu = new Reu();
        reu.setNome(reuRequestBody.getNome());
        Reu savedReu = testEntityManager.persistAndFlush(reu);

        reuRepository.deleteById(savedReu.getId());
        Optional<Reu> foundReu = reuRepository.findById(savedReu.getId());

        assertThat(foundReu).isNotPresent();
    }
}