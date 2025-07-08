package br.com.mapmarker.services;

import br.com.mapmarker.exceptions.CustomException;
import br.com.mapmarker.mappers.MarkerMapper;
import br.com.mapmarker.models.dtos.MarkerDto;
import br.com.mapmarker.repositories.MarkerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkerService {

    private final MarkerRepository repository;

    public List<MarkerDto> findAllMarkers() {
        return repository.findAll()
                .stream().map(MarkerMapper::convertEntityToDto).toList();
    }

    @Transactional
    public void createMarker(MarkerDto marker) {
        final var markerEntity = MarkerMapper.convertDtoToEntity(marker);

        repository.save(markerEntity);
    }

    @Transactional
    public void deleteMarker(Long id) {
        if (!repository.existsById(id))
            throw new CustomException(HttpStatus.NOT_FOUND, "marker.not_found_by_id", id);

        repository.deleteById(id);
    }

}
