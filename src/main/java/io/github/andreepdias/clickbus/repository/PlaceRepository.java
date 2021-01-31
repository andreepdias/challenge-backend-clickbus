package io.github.andreepdias.clickbus.repository;

import io.github.andreepdias.clickbus.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findAllByNameContainingIgnoreCase(String name);
}
