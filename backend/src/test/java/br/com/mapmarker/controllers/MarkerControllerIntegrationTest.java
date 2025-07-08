package br.com.mapmarker.controllers;

import br.com.mapmarker.models.dtos.MarkerDto;
import br.com.mapmarker.models.entities.MarkerEntity;
import br.com.mapmarker.repositories.MarkerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Testes de Integração para MarkerController")
class MarkerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MarkerRepository markerRepository;

    @AfterEach
    void tearDown() {
        markerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um marcador e retorná-lo na busca subsequente")
    @Transactional
    void shouldCreateMarkerAndRetrieveIt() throws Exception {
        final var markerDto = Instancio.of(MarkerDto.class)
                .ignore(Select.field(MarkerDto::id))
                .generate(Select.field(MarkerDto::latitude), gen -> gen.doubles().range(-90.0, 90.0))
                .generate(Select.field(MarkerDto::longitude), gen -> gen.doubles().range(-180.0, 180.0))
                .create();

        mockMvc.perform(post("/v1/markers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(markerDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/v1/markers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].latitude", closeTo(markerDto.latitude(), 0.000001)))
                .andExpect(jsonPath("$[0].longitude", closeTo(markerDto.longitude(), 0.000001)))
                .andExpect(jsonPath("$[0].title", is(markerDto.title())))
                .andExpect(jsonPath("$[0].text", is(markerDto.text())));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao tentar criar marcador com dados inválidos")
    void shouldReturnBadRequestWhenCreatingInvalidMarker() throws Exception {
        final var invalidMarkerDto = Instancio.of(MarkerDto.class)
                .ignore(Select.field(MarkerDto::id))
                .set(Select.field(MarkerDto::latitude), -90.1)
                .create();

        mockMvc.perform(post("/v1/markers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMarkerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Deve deletar um marcador existente com sucesso")
    @Transactional
    void shouldDeleteExistingMarkerSuccessfully() throws Exception {
        final var markerEntity = Instancio.of(MarkerEntity.class)
                .ignore(Select.field(MarkerEntity::getId))
                .create();
        final var savedMarker = markerRepository.save(markerEntity);

        mockMvc.perform(delete("/v1/markers/{id}", savedMarker.getId())).andExpect(status().isOk());

        final var deletedMarker = markerRepository.findById(savedMarker.getId());
        assertFalse(deletedMarker.isPresent());
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao tentar deletar com ID inválido")
    void shouldReturnBadRequestWhenDeletingWithInvalidId() throws Exception {
        mockMvc.perform(delete("/v1/markers/{id}", -1L))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há marcadores no banco de dados")
    void shouldReturnEmptyListWhenNoMarkersInDatabase() throws Exception {
        mockMvc.perform(get("/v1/markers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(content().json("[]"));
    }

}
