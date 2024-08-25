package com.thiago.promad.controller;

import com.thiago.promad.entity.Reu;
import com.thiago.promad.requests.ReuRequestBody;
import com.thiago.promad.service.ReuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reu")
public class ReuController {

    @Autowired
    private ReuService reuService;

    @GetMapping
    public ResponseEntity<Page<Reu>> getAllReus(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(reuService.getAllReus(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reu> getIdProcesso(@PathVariable Long id) {
        return ResponseEntity.ok(reuService.getIdReu(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        reuService.deleteReu(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reu> updateReu(@PathVariable Long id, @RequestBody @Valid ReuRequestBody reuRequestBody) {
        return ResponseEntity.ok(reuService.updateReu(id, reuRequestBody));
    }
}
