package com.thiago.promad.service;

import com.thiago.promad.entity.Processo;
import com.thiago.promad.exception.ExistsException;
import com.thiago.promad.exception.NotFoundException;
import com.thiago.promad.mapper.ProcessoMapper;
import com.thiago.promad.repository.ProcessoRepository;
import com.thiago.promad.repository.ReuRepository;
import com.thiago.promad.requests.ProcessoRequestBody;
import com.thiago.promad.util.ProcessoRequestBodyCreator;
import com.thiago.promad.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProcessoServiceTest {

    @InjectMocks
    ProcessoService processoService;

    @Mock
    ProcessoRepository processoRepository;

    @Mock
    ReuRepository reuRepository;

    @Mock
    ProcessoMapper processoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateProcesso() {
        ProcessoRequestBody processoRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        Processo processo = new Processo();
        processo.setNumeros(processoRequestBody.getNumeros());
        processo.setReu(processoRequestBody.getReu());

        when(processoMapper.toProcesso(processoRequestBody)).thenReturn(processo);
        when(processoRepository.findByNumerosIn(any(List.class))).thenReturn(null);
        when(processoRepository.save(any(Processo.class))).thenReturn(processo);

        Processo savedProcesso = processoService.createProcesso(processoRequestBody);

        assertThat(savedProcesso).isNotNull();
        assertThat(savedProcesso.getNumeros()).containsExactlyInAnyOrderElementsOf(processoRequestBody.getNumeros());
    }

    @Test
    public void testGetAllProcessos() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        when(processoRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(TestUtil.createProcesso())));

        Page<Processo> processos = processoService.getAllProcessos(0);

        assertThat(processos).isNotNull();
        assertThat(processos.getContent()).hasSize(1);
    }

    @Test
    public void testGetIdProcesso() {
        Processo processo = TestUtil.createProcesso();

        when(processoRepository.findById(anyLong())).thenReturn(Optional.of(processo));

        Processo foundProcesso = processoService.getIdProcesso(1L);

        assertThat(foundProcesso).isNotNull();
        assertThat(foundProcesso.getNumeros()).containsExactlyInAnyOrderElementsOf(processo.getNumeros());
    }

    @Test
    public void testDeleteProcesso() {
        doNothing().when(processoRepository).deleteById(anyLong());
        when(processoRepository.existsById(anyLong())).thenReturn(true);

        processoService.deleteProcesso(1L);

        verify(processoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testUpdateProcesso() {
        ProcessoRequestBody processoAtualizadoRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        Processo processoExistente = new Processo();
        processoExistente.setNumeros(processoAtualizadoRequestBody.getNumeros());
        processoExistente.setReu(processoAtualizadoRequestBody.getReu());

        when(processoMapper.toProcesso(processoAtualizadoRequestBody)).thenReturn(processoExistente);
        when(processoRepository.findById(anyLong())).thenReturn(Optional.of(processoExistente));
        when(processoRepository.save(any(Processo.class))).thenReturn(processoExistente);

        Processo updatedProcesso = processoService.updateProcesso(1L, processoAtualizadoRequestBody);

        assertThat(updatedProcesso).isNotNull();
        assertThat(updatedProcesso.getNumeros()).containsExactlyInAnyOrderElementsOf(processoAtualizadoRequestBody.getNumeros());
    }

    @Test
    public void testAddReuToProcesso() {
        ProcessoRequestBody processoComReuRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        Processo processoExistente = new Processo();
        processoExistente.setNumeros(processoComReuRequestBody.getNumeros());
        processoExistente.setReu(processoComReuRequestBody.getReu());

        when(processoMapper.toProcesso(processoComReuRequestBody)).thenReturn(processoExistente);
        when(processoRepository.findById(anyLong())).thenReturn(Optional.of(processoExistente));
        when(reuRepository.findByNome(any(String.class))).thenReturn(Optional.empty());
        when(processoRepository.save(any(Processo.class))).thenReturn(processoExistente);

        Processo updatedProcesso = processoService.addReuToProcesso(1L, processoComReuRequestBody);

        assertThat(updatedProcesso).isNotNull();
        assertThat(updatedProcesso.getReu()).isNotNull();
        assertThat(updatedProcesso.getReu().getNome()).isEqualTo("Test Reu");
    }

    @Test
    public void testCreateProcessoExistsException() {
        ProcessoRequestBody processoRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        Processo processo = new Processo();
        processo.setNumeros(processoRequestBody.getNumeros());
        processo.setReu(processoRequestBody.getReu());

        when(processoMapper.toProcesso(processoRequestBody)).thenReturn(processo);
        when(processoRepository.findByNumerosIn(any(List.class))).thenReturn(processo);

        assertThrows(ExistsException.class, () -> processoService.createProcesso(processoRequestBody));
    }

    @Test
    public void testGetIdProcessoNotFoundException() {
        when(processoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> processoService.getIdProcesso(1L));
    }

    @Test
    public void testDeleteProcessoNotFoundException() {
        when(processoRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> processoService.deleteProcesso(1L));
    }

    @Test
    public void testUpdateProcessoNotFoundException() {
        ProcessoRequestBody processoAtualizadoRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();

        when(processoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> processoService.updateProcesso(1L, processoAtualizadoRequestBody));
    }

    @Test
    public void testAddReuToProcessoNotFoundException() {
        ProcessoRequestBody processoComReuRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();

        when(processoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> processoService.addReuToProcesso(1L, processoComReuRequestBody));
    }

    @Test
    public void testAddReuToProcessoExistsException() {
        ProcessoRequestBody processoComReuRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        Processo processoExistente = new Processo();
        processoExistente.setNumeros(processoComReuRequestBody.getNumeros());
        processoExistente.setReu(processoComReuRequestBody.getReu());

        when(processoMapper.toProcesso(processoComReuRequestBody)).thenReturn(processoExistente);
        when(processoRepository.findById(anyLong())).thenReturn(Optional.of(processoExistente));
        when(reuRepository.findByNome(any(String.class))).thenReturn(Optional.of(processoExistente.getReu()));

        assertThrows(ExistsException.class, () -> processoService.addReuToProcesso(1L, processoComReuRequestBody));
    }
}