package br.com.mapmarker.controllers;

import br.com.mapmarker.models.dtos.MarkerDto;
import br.com.mapmarker.services.MarkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/markers")
@Tag(name = "Markers", description = "Operações relacionadas a marcadores geográficos")
public class MarkerController {

    private final MarkerService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retorna todos os marcadores", description = "Busca e lista todos os marcadores geográficos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de marcadores retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MarkerDto.class)))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<MarkerDto>> findAll() {
        return ResponseEntity.ok(service.findAllMarkers());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo marcador", description = "Adiciona um novo marcador ao sistema com base nos dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marcador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erros de validação ou dados incorretos)"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> createMarker(@RequestBody @Valid MarkerDto marker) {
        service.createMarker(marker);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remove um marcador", description = "Remove um marcador existente do sistema com base no seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marcador removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "id inválido (por exemplo, não positivo)"),
            @ApiResponse(responseCode = "404", description = "Marcador não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteMarker(
            @Parameter(description = "id do marcador a ser deletado", example = "123")
            @PathVariable @Positive(message = "marker.invalid_id") Long id) {
        service.deleteMarker(id);
        return ResponseEntity.ok().build();
    }

}