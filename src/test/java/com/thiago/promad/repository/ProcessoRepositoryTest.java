package com.thiago.promad.repository;

import com.thiago.promad.entity.Processo;
import com.thiago.promad.entity.Reu;
import com.thiago.promad.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProcessoRepositoryTest {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindByNumerosIn() {
        Processo processo = TestUtil.createProcesso();
        testEntityManager.persistAndFlush(processo);

        Processo foundProcesso = processoRepository.findByNumerosIn(processo.getNumeros());

        assertThat(foundProcesso).isNotNull();
        assertThat(foundProcesso.getNumeros()).containsExactlyInAnyOrderElementsOf(processo.getNumeros());
    }

    @Test
    public void testSave() {
        Processo processo = TestUtil.createProcesso();
        Processo savedProcesso = processoRepository.save(processo);

        assertThat(savedProcesso).isNotNull();
        assertThat(savedProcesso.getNumeros()).containsExactlyInAnyOrderElementsOf(processo.getNumeros());
    }

    @Test
    public void testFindAll() {
        Processo processo1 = TestUtil.createProcesso();
        testEntityManager.persistAndFlush(processo1);

        Processo processo2 = TestUtil.createProcesso2();
        testEntityManager.persistAndFlush(processo2);

        Iterable<Processo> processos = processoRepository.findAll();

        assertThat(processos).hasSize(2);
    }

    @Test
    public void testFindById() {
        Reu reu = TestUtil.createReu();
        testEntityManager.persistAndFlush(reu);

        Processo processo = TestUtil.createProcesso();
        processo.setReu(reu);
        Processo savedProcesso = testEntityManager.persistAndFlush(processo);

        Optional<Processo> foundProcesso = processoRepository.findById(savedProcesso.getId());

        assertThat(foundProcesso).isPresent();
        assertThat(foundProcesso.get().getNumeros()).containsExactlyInAnyOrderElementsOf(processo.getNumeros());
    }

    @Test
    public void testExistsById() {
        Processo processo = TestUtil.createProcesso();
        Processo savedProcesso = testEntityManager.persistAndFlush(processo);

        boolean exists = processoRepository.existsById(savedProcesso.getId());

        assertThat(exists).isTrue();
    }

    @Test
    public void testDeleteById() {
        Processo processo = TestUtil.createProcesso();
        Processo savedProcesso = testEntityManager.persistAndFlush(processo);

        processoRepository.deleteById(savedProcesso.getId());
        boolean exists = processoRepository.existsById(savedProcesso.getId());

        assertThat(exists).isFalse();
    }

    @Test
    public void testFindByNome() {
        Reu reu = TestUtil.createReu();
        testEntityManager.persistAndFlush(reu);

        Optional<Reu> foundReu = reuRepository.findByNome(reu.getNome());

        assertThat(foundReu).isPresent();
        assertThat(foundReu.get().getNome()).isEqualTo(reu.getNome());
    }
}