package com.thiago.promad.controller;

import com.thiago.promad.entity.Processo;
import com.thiago.promad.requests.ProcessoRequestBody;
import com.thiago.promad.service.ProcessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<Processo> createProcesso(@RequestBody @Valid ProcessoRequestBody processoRequestBody) {
        return new ResponseEntity<>(processoService.createProcesso(processoRequestBody), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Processo>> getAllProcessos(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(processoService.getAllProcessos(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> getIdProcesso(@PathVariable Long id) {
        return ResponseEntity.ok(processoService.getIdProcesso(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        processoService.deleteProcesso(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processo> atualizarProcesso(@PathVariable Long id, @RequestBody ProcessoRequestBody processo) {
        return ResponseEntity.ok(processoService.updateProcesso(id, processo));
    }

    @PutMapping("/{id}/reu")
    public ResponseEntity<Processo> addReuToProcesso(@PathVariable Long id, @RequestBody @Valid ProcessoRequestBody processo) {
        return new ResponseEntity<>(processoService.addReuToProcesso(id, processo), HttpStatus.CREATED);
    }
}
