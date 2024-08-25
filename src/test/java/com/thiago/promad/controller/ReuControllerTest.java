package com.thiago.promad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.promad.entity.Reu;
import com.thiago.promad.requests.ReuRequestBody;
import com.thiago.promad.service.ReuService;
import com.thiago.promad.util.ReuRequestBodyCreator;
import com.thiago.promad.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReuService reuService;

    @BeforeEach
    public void setup() {
        Reu reu = TestUtil.createReu();

        when(reuService.getAllReus(anyInt())).thenReturn(new PageImpl<>(Arrays.asList(reu)));
        when(reuService.getIdReu(anyLong())).thenReturn(reu);
        when(reuService.updateReu(anyLong(), ArgumentMatchers.any(ReuRequestBody.class))).thenReturn(reu);
    }

    @Test
    public void testGetAllReus() throws Exception {
        mockMvc.perform(get("/reu"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIdReu() throws Exception {
        mockMvc.perform(get("/reu/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteReu() throws Exception {
        mockMvc.perform(delete("/reu/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateReu() throws Exception {
        ReuRequestBody reuRequestBody = ReuRequestBodyCreator.createReuRequestBody();
        mockMvc.perform(put("/reu/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reuRequestBody)))
                .andExpect(status().isOk());
    }
}