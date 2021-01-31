package io.github.andreepdias.clickbus.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class HandleDateTest {

    @Test
    @DisplayName("Should return a local date time when given a date time string.")
    void stringToLocalDate(){
        String dateTime = "2021-01-31T10:56:11";

        LocalDateTime localDateTime = HandleDate.StringToLocalDateTime(dateTime);

        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2021, 01, 31, 10, 56, 11));
    }

    @Test
    @DisplayName("Should return null when given a date time string null.")
    void nullStringToLocalDate(){
        String dateTime = null;

        LocalDateTime localDateTime = HandleDate.StringToLocalDateTime(dateTime);

        assertThat(localDateTime).isNull();
    }

    @Test
    @DisplayName("Should return a local date time when given a date time string.")
    void localDateTimeToString(){
        LocalDateTime dateTime = LocalDateTime.of(2021, 01, 31, 10, 56, 11);

        String dateTimeString = HandleDate.LocalDateTimeToString(dateTime);

        assertThat(dateTimeString).isEqualTo("2021-01-31T10:56:11");
    }

    @Test
    @DisplayName("Should return a local date time when given a date time string.")
    void notLocalDateTimeToString(){
        LocalDateTime dateTime = null;

        String dateTimeString = HandleDate.LocalDateTimeToString(dateTime);

        assertThat(dateTimeString).isNull();
    }


}