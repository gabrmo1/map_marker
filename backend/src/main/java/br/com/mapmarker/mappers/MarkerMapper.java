package br.com.mapmarker.mappers;

import br.com.mapmarker.models.dtos.MarkerDto;
import br.com.mapmarker.models.entities.MarkerEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MarkerMapper {

    public static MarkerEntity convertDtoToEntity(MarkerDto dto) {
        return MarkerEntity.builder()
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .title(dto.title())
                .text(dto.text())
                .build();
    }

    public static MarkerDto convertEntityToDto(MarkerEntity entity) {
        return MarkerDto.builder()
                .id(entity.getId())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .title(entity.getTitle())
                .text(entity.getText())
                .build();
    }

}
