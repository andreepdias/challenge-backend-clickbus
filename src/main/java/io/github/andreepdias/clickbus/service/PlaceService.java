package io.github.andreepdias.clickbus.service;

import io.github.andreepdias.clickbus.exception.ObjectNotFoundException;
import io.github.andreepdias.clickbus.domain.Place;
import io.github.andreepdias.clickbus.repository.PlaceRepository;
import io.github.andreepdias.clickbus.resource.dto.PlaceDTO;
import io.github.andreepdias.clickbus.util.HandleDate;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository repository;
    private final Clock clock;

    public PlaceService(PlaceRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public PlaceDTO getById(Long id) {
        Place entity = findById(id);
        return toDTO(entity);
    }

    public List<PlaceDTO> getAll() {
        List<Place> entities = repository.findAll();
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<PlaceDTO> getByName(String name) {
        List<Place> entities = repository.findAllByNameContainingIgnoreCase(name);
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PlaceDTO create(PlaceDTO dto) {
        Place entity = toEntity(dto);
        entity.setId(null);
        entity.updateCreationMoment(clock);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    public PlaceDTO update(PlaceDTO dto, Long id) {
        Place entity = findById(id);
        updateEntityData(entity, dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    private void updateEntityData(Place entity, PlaceDTO dto) {
        entity.setName(dto.getName());
        entity.setSlang(dto.getSlang());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.updateAtualizationMoment(clock);
    }

    private Place findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Place with id " + id + " was not found."));
    }

    private Place toEntity(PlaceDTO dto) {
        return new Place(null, dto.getName(), dto.getSlang(), dto.getCity(), dto.getState(), null, null);
    }

    private PlaceDTO toDTO(Place entity) {
        String createdAt = HandleDate.LocalDateTimeToString(entity.getCreatedAt());
        String updatedAt = HandleDate.LocalDateTimeToString(entity.getUpdatedAt());
        return new PlaceDTO(entity.getId(), entity.getName(), entity.getSlang(), entity.getCity(), entity.getState(), createdAt, updatedAt);
    }
}
