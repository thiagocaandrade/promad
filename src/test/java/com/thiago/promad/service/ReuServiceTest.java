package com.thiago.promad.service;

import com.thiago.promad.entity.Reu;
import com.thiago.promad.exception.NotFoundException;
import com.thiago.promad.mapper.ReuMapper;
import com.thiago.promad.repository.ReuRepository;
import com.thiago.promad.requests.ReuRequestBody;
import com.thiago.promad.util.ReuRequestBodyCreator;
import com.thiago.promad.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReuServiceTest {

    @InjectMocks
    ReuService reuService;

    @Mock
    ReuRepository reuRepository;

    @Mock
    ReuMapper reuMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllReus() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        when(reuRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(TestUtil.createReu())));

        var reus = reuService.getAllReus(0);

        assertThat(reus).isNotNull();
        assertThat(reus.getContent()).hasSize(1);
    }

    @Test
    public void testGetIdReu() {
        when(reuRepository.findById(anyLong())).thenReturn(Optional.of(TestUtil.createReu()));

        var reu = reuService.getIdReu(1L);

        assertThat(reu).isNotNull();
        assertThat(reu.getNome()).isEqualTo("Test Reu");
    }

    @Test
    public void testGetIdReuNotFoundException() {
        when(reuRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reuService.getIdReu(1L));
    }

    @Test
    public void testDeleteReu() {
        when(reuRepository.existsById(anyLong())).thenReturn(true);

        reuService.deleteReu(1L);

        verify(reuRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteReuNotFoundException() {
        when(reuRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> reuService.deleteReu(1L));
    }

    @Test
    public void testUpdateReu() {
        ReuRequestBody reuRequestBody = ReuRequestBodyCreator.createReuRequestBody();
        Reu reu = new Reu();
        reu.setNome(reuRequestBody.getNome());
        Reu updatedReu = new Reu();
        updatedReu.setNome("Updated Reu");

        when(reuRepository.findById(anyLong())).thenReturn(Optional.of(reu));
        when(reuMapper.toReu(reuRequestBody)).thenReturn(updatedReu);
        when(reuRepository.save(any(Reu.class))).thenReturn(updatedReu);

        Reu result = reuService.updateReu(1L, reuRequestBody);

        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("Updated Reu");
    }
}