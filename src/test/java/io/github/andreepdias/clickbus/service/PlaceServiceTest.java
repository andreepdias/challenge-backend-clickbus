package io.github.andreepdias.clickbus.service;

import io.github.andreepdias.clickbus.domain.Place;
import io.github.andreepdias.clickbus.exception.ObjectNotFoundException;
import io.github.andreepdias.clickbus.repository.PlaceRepository;
import io.github.andreepdias.clickbus.resource.dto.PlaceDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.github.andreepdias.clickbus.shared.PlaceUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PlaceServiceTest {

    @Autowired
    private PlaceService service;

    @MockBean
    private PlaceRepository repository;

    @MockBean
    private Clock clock;

    @Test
    @DisplayName("Should get a place dto from a given id.")
    void getById(){
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(createPlaceEntity()));
        PlaceDTO dto = service.getById(id);

        assertThat(dto).usingRecursiveComparison().isEqualTo(createPlaceDTO());
    }

    @Test
    @DisplayName("Shout not get a place dto from a non-existent id.")
    void notGetById(){
        Long id = 2L;

        when(repository.findById(id)).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> {
            PlaceDTO dto = service.getById(id);
        });
    }

    @Test
    @DisplayName("Should get a list of places dto.")
    void getAll(){
        when(repository.findAll()).thenReturn(createPlaceListEntity());
        List<PlaceDTO> placeDTOS = service.getAll();

        assertThat(placeDTOS).usingRecursiveComparison().isEqualTo(createPlaceListDTO());
    }

    @Test
    @DisplayName("Should get a empty list of places dto.")
    void getNothing(){
        when(repository.findAll()).thenReturn(new ArrayList<>());
        List<PlaceDTO> placeDTOS = service.getAll();

        assertTrue(placeDTOS.isEmpty());
    }

    @Test
    @DisplayName("Should get a list of places dto from a given name.")
    void getByName(){
        String name = "mirante";

        when(repository.findAllByNameContainingIgnoreCase(name)).thenReturn(Arrays.asList(createPlaceEntity()));
        List<PlaceDTO> placeDTOS = service.getByName(name);

        assertThat(placeDTOS).usingRecursiveComparison().isEqualTo(Arrays.asList(createPlaceDTO()));
    }

    @Test
    @DisplayName("Should get a empty list of places dto from a given name.")
    void getNothingByName(){
        String name = "mirante123";

        when(repository.findAllByNameContainingIgnoreCase(name)).thenReturn(new ArrayList<>());
        List<PlaceDTO> placeDTOS = service.getByName(name);

        assertTrue(placeDTOS.isEmpty());
    }

    @Test
    @DisplayName("Should create a place and return its dto.")
    void create(){
        PlaceDTO dto = createPlaceDTO();
        Instant instant = createDateTimeInstant();

        when(clock.instant()).thenReturn(instant);
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(repository.save(any(Place.class))).thenReturn(createPlaceEntity());
        dto = service.create(dto);

        assertThat(dto).usingRecursiveComparison().isEqualTo(createPlaceDTO());
    }

    @Test
    @DisplayName("Should update a place and return its dto.")
    void update(){
        PlaceDTO dto = createPlaceDTO();
        Long id = dto.getId();
        Instant instant = createDateTimeInstant();

        when(clock.instant()).thenReturn(instant);
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(repository.findById(dto.getId())).thenReturn(Optional.of(createPlaceEntity()));
        when(repository.save(any(Place.class))).thenReturn(createPlaceEntity());
        dto = service.update(dto, id);

        assertThat(dto).usingRecursiveComparison().isEqualTo(createPlaceDTO());
    }

    @Test
    @DisplayName("Should not update a place, and throws an exception.")
    void notUpdate(){
        PlaceDTO dto = createPlaceDTO();
        Long id = dto.getId();

        when(repository.findById(dto.getId())).thenThrow(ObjectNotFoundException.class);
        when(repository.save(any())).thenReturn(createPlaceEntity());

        assertThrows(ObjectNotFoundException.class, () -> {
            service.update(dto, id);
        });
    }
}