package br.com.mapmarker.repositories;

import br.com.mapmarker.models.entities.MarkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepository extends JpaRepository<MarkerEntity, Long> {
}
