package br.com.mapmarker.services;

import br.com.mapmarker.exceptions.CustomException;
import br.com.mapmarker.mappers.MarkerMapper;
import br.com.mapmarker.models.dtos.MarkerDto;
import br.com.mapmarker.models.entities.MarkerEntity;
import br.com.mapmarker.repositories.MarkerRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para MarkerService")
class MarkerServiceTest {

    @Mock
    private MarkerRepository markerRepository;

    @InjectMocks
    private MarkerService markerService;

    private MarkerDto testMarkerDto;
    private MarkerEntity testMarkerEntity;

    private MockedStatic<MarkerMapper> mockedStaticMarkerMapper;

    @BeforeEach
    void setUp() {
        testMarkerDto = Instancio.of(MarkerDto.class)
                .generate(field(MarkerDto::latitude), gen -> gen.doubles().range(-90.0, 90.0))
                .generate(field(MarkerDto::longitude), gen -> gen.doubles().range(-180.0, 180.0))
                .create();

        testMarkerEntity = Instancio.of(MarkerEntity.class)
                .set(field(MarkerEntity::getId), testMarkerDto.id() != null ? testMarkerDto.id() : Instancio.create(Long.class))
                .set(field(MarkerEntity::getLatitude), testMarkerDto.latitude())
                .set(field(MarkerEntity::getLongitude), testMarkerDto.longitude())
                .set(field(MarkerEntity::getTitle), testMarkerDto.title())
                .set(field(MarkerEntity::getText), testMarkerDto.text())
                .create();

        mockedStaticMarkerMapper = mockStatic(MarkerMapper.class);

        mockedStaticMarkerMapper.when(() -> MarkerMapper.convertEntityToDto(any(MarkerEntity.class)))
                .thenReturn(testMarkerDto);

        mockedStaticMarkerMapper.when(() -> MarkerMapper.convertDtoToEntity(any(MarkerDto.class)))
                .thenReturn(testMarkerEntity);
    }

    @AfterEach
    void tearDown() {
        if (mockedStaticMarkerMapper != null) {
            mockedStaticMarkerMapper.close();
        }
    }

    @Test
    @DisplayName("findAllMarkers: Deve retornar todos os marcadores com sucesso quando existem")
    void findAllMarkers_shouldReturnAllMarkersSuccessfully() {
        when(markerRepository.findAll()).thenReturn(List.of(testMarkerEntity));

        final var result = markerService.findAllMarkers();

        assertNotNull(result, "A lista de marcadores não deve ser nula.");
        assertFalse(result.isEmpty(), "A lista de marcadores não deve estar vazia.");
        assertEquals(1, result.size(), "Deve retornar um marcador.");
        assertEquals(testMarkerDto.id(), result.get(0).id(), "O ID do marcador retornado deve ser o esperado.");
        assertEquals(testMarkerDto.title(), result.get(0).title(), "O título do marcador retornado deve ser o esperado.");

        verify(markerRepository, times(1)).findAll();
        mockedStaticMarkerMapper.verify(() -> MarkerMapper.convertEntityToDto(testMarkerEntity), times(1));
    }

    @Test
    @DisplayName("findAllMarkers: Deve retornar uma lista vazia quando não há marcadores")
    void findAllMarkers_shouldReturnEmptyListWhenNoMarkers() {
        when(markerRepository.findAll()).thenReturn(Collections.emptyList());

        final var result = markerService.findAllMarkers();

        assertNotNull(result, "A lista de marcadores não deve ser nula.");
        assertTrue(result.isEmpty(), "A lista de marcadores deve estar vazia.");

        verify(markerRepository, times(1)).findAll();
        mockedStaticMarkerMapper.verify(() -> MarkerMapper.convertEntityToDto(any(MarkerEntity.class)), never());
    }

    @Test
    @DisplayName("createMarker: Deve criar um novo marcador com sucesso")
    void createMarker_shouldCreateMarkerSuccessfully() {
        when(markerRepository.save(any(MarkerEntity.class))).thenReturn(testMarkerEntity);

        markerService.createMarker(testMarkerDto);

        mockedStaticMarkerMapper.verify(() -> MarkerMapper.convertDtoToEntity(testMarkerDto), times(1));
        verify(markerRepository, times(1)).save(testMarkerEntity);
    }

    @Test
    @DisplayName("deleteMarker: Deve deletar um marcador existente com sucesso")
    void deleteMarker_shouldDeleteExistingMarkerSuccessfully() {
        when(markerRepository.existsById(testMarkerDto.id())).thenReturn(true);

        markerService.deleteMarker(testMarkerDto.id());

        verify(markerRepository, times(1)).existsById(testMarkerDto.id());
        verify(markerRepository, times(1)).deleteById(testMarkerDto.id());
    }

    @Test
    @DisplayName("deleteMarker: Deve lançar CustomException quando o marcador não é encontrado para deleção")
    void deleteMarker_shouldThrowCustomExceptionWhenMarkerNotFound() {
        final var nonExistentId = Instancio.create(Long.class);

        when(markerRepository.existsById(nonExistentId)).thenReturn(false);

        final var thrown = assertThrows(CustomException.class, () ->
                        markerService.deleteMarker(nonExistentId),
                "Deve lançar CustomException quando o marcador não é encontrado.");

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus(), "O status da exceção deve ser NOT_FOUND.");
        assertEquals("marker.not_found_by_id", thrown.getMessageKey(), "O código da exceção deve ser 'marker.not_found_by_id'.");
        assertNotNull(thrown.getArgs(), "Os argumentos da exceção não devem ser nulos.");
        assertEquals(1, thrown.getArgs().length, "Deve haver um argumento na exceção.");
        assertEquals(nonExistentId, thrown.getArgs()[0], "O primeiro argumento da exceção deve ser o ID não encontrado.");

        verify(markerRepository, times(1)).existsById(nonExistentId);
        verify(markerRepository, never()).deleteById(anyLong());
    }

}