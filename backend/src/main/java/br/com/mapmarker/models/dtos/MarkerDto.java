package br.com.mapmarker.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record MarkerDto(

        Long id,

        @NotNull(message = "A latitude deve ser preenchida.")
        @DecimalMin(value = "-90.0", message = "A latitude não pode ser menor que -90.")
        @DecimalMax(value = "90.0", message = "A latitude não pode ser maior que 90.")
        Double latitude,

        @NotNull(message = "A longitude deve ser preenchida.")
        @DecimalMin(value = "-180.0", message = "A longitude não pode ser menor que -180")
        @DecimalMax(value = "180.0", message = "A longitude não pode ser maior que 180")
        Double longitude,

        @NotBlank(message = "O título não pode ser vazio.")
        @NotEmpty(message = "O título não pode ser vazio.")
        String title,

        @NotBlank(message = "O texto não pode ser vazio.")
        @NotEmpty(message = "O texto não pode ser vazio.")
        String text

) {
}
