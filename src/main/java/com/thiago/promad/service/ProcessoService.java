package com.thiago.promad.service;

import com.thiago.promad.entity.Processo;
import com.thiago.promad.entity.Reu;
import com.thiago.promad.exception.ExistsException;
import com.thiago.promad.exception.NotFoundException;
import com.thiago.promad.mapper.ProcessoMapper;
import com.thiago.promad.repository.ProcessoRepository;
import com.thiago.promad.repository.ReuRepository;
import com.thiago.promad.requests.ProcessoRequestBody;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private ProcessoMapper processoMapper;

    public Processo createProcesso(ProcessoRequestBody processoRequestBody) {
        Processo processo = processoMapper.toProcesso(processoRequestBody);
        Processo existingProcessos = processoRepository.findByNumerosIn(processo.getNumeros());
        if (existingProcessos != null) {
            throw new ExistsException("Um ou mais números de processo já existem");
        }

        if(Objects.nonNull(processo.getReu()) && Objects.nonNull(processo.getReu().getNome())) {
            reuRepository.findByNome(processo.getReu().getNome())
                    .ifPresent(r -> {
                        throw new ExistsException("Réu com o nome " + processo.getReu().getNome() + " já existe");
                    });
        }

        return processoRepository.save(processo);
    }

    public Page<Processo> getAllProcessos(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("id"));
        return processoRepository.findAll(pageable);
    }

    public Processo getIdProcesso(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo com o id " + id + " não encontrado"));
    }

    public void deleteProcesso(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new NotFoundException("Processo com o id " + id + " não encontrado");
        }
        processoRepository.deleteById(id);
    }

    public Processo updateProcesso(Long id, ProcessoRequestBody processoAtualizadoRequestBody) {
        Processo processoAtualizado = processoMapper.toProcesso(processoAtualizadoRequestBody);
        Processo processoExistente = processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo com o id " + id + " não encontrado"));

        processoExistente.setNumeros(processoAtualizado.getNumeros());

        return processoRepository.save(processoExistente);
    }

    public Processo addReuToProcesso(Long id, ProcessoRequestBody processoRequestBody) {
        Processo processo = processoMapper.toProcesso(processoRequestBody);
        Processo existingProcesso = processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo com o id " + id + " não encontrado"));

        if (processo.getReu() == null || processo.getReu().getNome() == null) {
            throw new IllegalArgumentException("Réu e nome do réu não podem ser nulos");
        }

        Optional<Reu> existingReu = reuRepository.findByNome(processo.getReu().getNome());
        if (existingReu.isPresent()) {
            throw new ExistsException("Réu com o nome " + processo.getReu().getNome() + " já existe");
        }

        existingProcesso.setReu(processo.getReu());
        BeanUtils.copyProperties(processo.getReu(), existingProcesso.getReu(), "id");

        return processoRepository.save(existingProcesso);
    }
}