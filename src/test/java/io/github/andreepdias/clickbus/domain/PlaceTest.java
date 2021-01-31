package io.github.andreepdias.clickbus.domain;

import io.github.andreepdias.clickbus.shared.PlaceUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;

import static io.github.andreepdias.clickbus.shared.PlaceUtil.*;
import static io.github.andreepdias.clickbus.shared.PlaceUtil.createPlaceEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PlaceTest {

    @MockBean
    private Clock clock;

    @Test
    @DisplayName("Should update place creationAt date time.")
    void updateCreationMoment(){
        Place place = new Place(null, "Mirante de Jlle", "Mirante", "Joinville", "SC", null, null);

        Instant instant = createDateTimeInstant();
        when(clock.instant()).thenReturn(instant);
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        place.updateCreationMoment(clock);

        assertThat(place.getCreatedAt()).isNotNull();
        assertThat(place.getCreatedAt()).isEqualTo(createDateTime());
    }

    @Test
    @DisplayName("Should update place updateAt date time.")
    void updateAtualizationMoment(){
        Place place = new Place(null, "Mirante de Jlle", "Mirante", "Joinville", "SC", createDateTime(), null);

        Instant instant = createDateTimeInstant();
        when(clock.instant()).thenReturn(instant);
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        place.updateAtualizationMoment(clock);

        assertThat(place.getUpdatedAt()).isNotNull();
        assertThat(place.getUpdatedAt()).isEqualTo(createDateTime());
    }
}