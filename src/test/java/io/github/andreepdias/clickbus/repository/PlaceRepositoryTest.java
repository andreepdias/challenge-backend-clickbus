package io.github.andreepdias.clickbus.repository;

import io.github.andreepdias.clickbus.domain.Place;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static io.github.andreepdias.clickbus.shared.PlaceUtil.createPlaceEntity;
import static io.github.andreepdias.clickbus.shared.PlaceUtil.createPlaceListEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PlaceRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private PlaceRepository repository;

    @Test
    @DisplayName("Should return a list of places which name matches a given name.")
    void findByName(){
        String name = "mirante";
        Place place = createPlaceEntity();
        place.setId(null);
        entityManager.persist(place);

        List<Place> places = repository.findAllByNameContainingIgnoreCase(name);

        assertFalse(places.isEmpty());
        assertTrue(places.size() == 1);
        assertThat(places.get(0)).usingRecursiveComparison().isEqualTo(place);
    }

    @Test
    @DisplayName("Should return a empty list of places when given name do not matches any place.")
    void notFindByName(){
        String name = "mirante123";
        Place place = createPlaceEntity();
        place.setId(null);
        entityManager.persist(place);

        List<Place> places = repository.findAllByNameContainingIgnoreCase(name);

        assertTrue(places.isEmpty());
    }

    @Test
    @DisplayName("Should return a optional of place when given an id.")
    void findById(){
        Long id = 1L;
        Place repositoryPlace = createPlaceEntity();
        repositoryPlace.setId(null);
        entityManager.persist(repositoryPlace);

        Optional<Place> place = repository.findById(id);

        assertThat(place.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(repositoryPlace);
    }

    @Test
    @DisplayName("Should return an empty optional when find by a non-existent id.")
    void notFindById(){
        Long id = 2L;

        Optional<Place> place = repository.findById(id);

        assertFalse(place.isPresent());
    }

    @Test
    @DisplayName("Should return a list of all places in the repository.")
    void findAll(){
        List<Place> repositoryPlaces = createPlaceListEntity();
        repositoryPlaces.forEach(x -> x.setId(null));
        repositoryPlaces.forEach(x -> entityManager.persist(x));

        List<Place> places = repository.findAll();

        assertTrue(places.size() == 2);
        assertThat(places).usingRecursiveComparison().ignoringFields("id").isEqualTo(repositoryPlaces);
    }

    @Test
    @DisplayName("Should return a empty list of places in a empty repository.")
    void findNothingAtAll(){
        List<Place> repositoryPlaces = createPlaceListEntity();

        List<Place> places = repository.findAll();

        assertTrue(places.isEmpty());
    }

    @Test
    @DisplayName("Should save and return a place in the repository.")
    void create(){
        Place place = createPlaceEntity();
        place.setId(null);

        place = repository.save(place);

        assertThat(place.getId()).isNotNull();
        assertThat(place).usingRecursiveComparison().ignoringFields("id").isEqualTo(createPlaceEntity());
    }

    @Test
    @DisplayName("Should update and return a place in the repository.")
    void update(){
        Place place = createPlaceEntity();
        place.setId(null);
        place = entityManager.persist(place);
        String newName = "My dear home.";
        place.setName(newName);

        place = repository.save(place);

        assertThat(place.getName()).isEqualTo(newName);
    }
}