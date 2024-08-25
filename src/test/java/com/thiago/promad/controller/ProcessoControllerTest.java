package com.thiago.promad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.promad.entity.Processo;
import com.thiago.promad.requests.ProcessoRequestBody;
import com.thiago.promad.service.ProcessoService;
import com.thiago.promad.util.ProcessoRequestBodyCreator;
import com.thiago.promad.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProcessoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessoService processoService;

    @BeforeEach
    public void setup() {
        Processo processo = TestUtil.createProcesso();

        when(processoService.createProcesso(any(ProcessoRequestBody.class))).thenReturn(processo);
        when(processoService.getAllProcessos(any(Integer.class))).thenReturn(new PageImpl<>(Arrays.asList(processo)));
        when(processoService.getIdProcesso(anyLong())).thenReturn(processo);
        when(processoService.updateProcesso(anyLong(), any(ProcessoRequestBody.class))).thenReturn(processo);
        when(processoService.addReuToProcesso(anyLong(), any(ProcessoRequestBody.class))).thenReturn(processo);
    }

    @Test
    public void testCreateProcesso() throws Exception {
        ProcessoRequestBody processoRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        mockMvc.perform(post("/processos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(processoRequestBody)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllProcessos() throws Exception {
        mockMvc.perform(get("/processos"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIdProcesso() throws Exception {
        mockMvc.perform(get("/processos/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProcesso() throws Exception {
        mockMvc.perform(delete("/processos/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAtualizarProcesso() throws Exception {
        ProcessoRequestBody processoRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        mockMvc.perform(put("/processos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(processoRequestBody)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddReuToProcesso() throws Exception {
        ProcessoRequestBody processoRequestBody = ProcessoRequestBodyCreator.createProcessoRequestBody();
        mockMvc.perform(put("/processos/1/reu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(processoRequestBody)))
                .andExpect(status().isCreated());
    }
}