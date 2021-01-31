package io.github.andreepdias.clickbus.shared;

import io.github.andreepdias.clickbus.domain.Place;
import io.github.andreepdias.clickbus.resource.dto.PlaceDTO;
import io.github.andreepdias.clickbus.util.HandleDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class PlaceUtil {

    public static Place createPlaceEntity(){
        return new Place(1L, "Mirante do Boa Vista", "Mirante", "Joinville", "SC", createDateTime(), createDateTime());
    }

    public static List<Place> createPlaceListEntity(){
        return Arrays.asList(
                createPlaceEntity(),
                new Place(2L, "Parque Municipal Morro do Finder", "Morro do Finder", "Joinville", "SC", createDateTime(), createDateTime())
        );
    }

    public static PlaceDTO createPlaceDTO(){
        return new PlaceDTO(1L, "Mirante do Boa Vista", "Mirante", "Joinville", "SC", createDateTimeString(), createDateTimeString());
    }

    public static List<PlaceDTO> createPlaceListDTO(){
        return Arrays.asList(
                createPlaceDTO(),
                new PlaceDTO(2L, "Parque Municipal Morro do Finder", "Morro do Finder", "Joinville", "SC", createDateTimeString(), createDateTimeString())
        );
    }

    public static LocalDateTime createDateTime(){
        return LocalDateTime.of(2021, 1, 30, 14, 28, 12);
    }

    public static String createDateTimeString(){
        LocalDateTime dateTime = createDateTime();
        return HandleDate.LocalDateTimeToString(dateTime);
    }

    public static Instant createDateTimeInstant(){
        LocalDateTime dateTime = createDateTime();
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }



}
