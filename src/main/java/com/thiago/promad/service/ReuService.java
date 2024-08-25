package com.thiago.promad.service;

import com.thiago.promad.entity.Reu;
import com.thiago.promad.exception.NotFoundException;
import com.thiago.promad.mapper.ReuMapper;
import com.thiago.promad.repository.ProcessoRepository;
import com.thiago.promad.repository.ReuRepository;
import com.thiago.promad.requests.ReuRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReuService {

    @Autowired
    private ReuRepository reuRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ReuMapper reuMapper;

    public Page<Reu> getAllReus(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("id"));
        return reuRepository.findAll(pageable);
    }

    public Reu getIdReu(Long id) {
        return reuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reu com o id " + id + " não encontrado"));
    }

    public void deleteReu(Long id) {
        if (!reuRepository.existsById(id)) {
            throw new NotFoundException("Reu com o id " + id + " não encontrado");
        }
        reuRepository.deleteById(id);
    }

    public Reu updateReu(Long id, ReuRequestBody reuRequestBody) {
        Reu reu = reuMapper.toReu(reuRequestBody);
        Reu existingReu = reuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reu com o id " + id + " não encontrado"));

        existingReu.setNome(reuRequestBody.getNome());
        return reuRepository.save(existingReu);
    }
}