package io.github.andreepdias.clickbus.resource.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.andreepdias.clickbus.exception.ObjectNotFoundException;
import io.github.andreepdias.clickbus.resource.dto.PlaceDTO;
import io.github.andreepdias.clickbus.service.PlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.andreepdias.clickbus.shared.PlaceUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlaceController.class)
@AutoConfigureMockMvc
class PlaceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaceService service;

    private final String PLACES_API = "/places/";

    @Test
    @DisplayName("Should return a place DTO.")
    void getById() throws Exception {

        Long id = 1L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PLACES_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        when(service.getById(id)).thenReturn(createPlaceDTO());

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id));
    }

    @Test
    @DisplayName("Should not return a place DTO given a non-existent id.")
    void notGetById() throws Exception {
        Long id = 2L;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PLACES_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        when(service.getById(id)).thenThrow(ObjectNotFoundException.class);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return a list of places DTO.")
    void getAll() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PLACES_API)
                .accept(MediaType.APPLICATION_JSON);

        List<PlaceDTO> responsePlaceDTOs = createPlaceListDTO();
        when(service.getAll()).thenReturn(responsePlaceDTOs);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return a list of places DTO filtered by name.")
    void getByName() throws Exception {
        String name = "morro";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PLACES_API)
                .param("name", name)
                .accept(MediaType.APPLICATION_JSON);

        List<PlaceDTO> responsePlaceDTOs = Arrays.asList(createPlaceDTO());
        when(service.getByName(name)).thenReturn(responsePlaceDTOs);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Should return a empty list of places DTO filtered by an unknown name.")
    void getByUnknownName() throws Exception {
        String name = "morro123";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PLACES_API)
                .param("name", name)
                .accept(MediaType.APPLICATION_JSON);

        List<PlaceDTO> responsePlaceDTOs = new ArrayList<>();
        when(service.getByName(name)).thenReturn(responsePlaceDTOs);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should create a place from a DTO and return its DTO.")
    void create() throws  Exception{

        PlaceDTO dto = new PlaceDTO(null, "Mirante de Joinville do Boa Vista", "Mirante", "Joinville", "SC", null, null);
        String json = DTOtoJSON(dto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PLACES_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        PlaceDTO responseDTO = new PlaceDTO(1L, "Mirante de Joinville do Boa Vista", "Mirante", "Joinville", "SC", createDateTimeString(), null);
        when(service.create(any(PlaceDTO.class))).thenReturn(responseDTO);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("slang").value(dto.getSlang()))
                .andExpect(jsonPath("city").value(dto.getCity()))
                .andExpect(jsonPath("state").value(dto.getState()))
                .andExpect(jsonPath("createdAt").isNotEmpty())
                .andExpect(jsonPath("updatedAt").isEmpty());
    }

    @Test
    @DisplayName("Should update a place from a DTO and return its DTO.")
    void update() throws  Exception{
        Long id = 1L;
        PlaceDTO dto = new PlaceDTO(1L, "Mirante de Joinville do Boa Vista", "Mirante", "Joinville", "SC", createDateTimeString(), null);
        String json = DTOtoJSON(dto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PLACES_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        PlaceDTO responseDTO = new PlaceDTO(1L, "Mirante de Joinville do Boa Vista", "Mirante", "Joinville", "SC", createDateTimeString(), createDateTimeString());
        when(service.update(any(PlaceDTO.class), any(Long.class))).thenReturn(responseDTO);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(dto.getId()))
                .andExpect(jsonPath("slang").value(dto.getSlang()))
                .andExpect(jsonPath("city").value(dto.getCity()))
                .andExpect(jsonPath("state").value(dto.getState()))
                .andExpect(jsonPath("createdAt").value(dto.getCreatedAt()))
                .andExpect(jsonPath("updatedAt").isNotEmpty());
    }

    @Test
    @DisplayName("Should nto update a place given a non-existent id.")
    void notUpdate() throws  Exception{
        Long id = 2L;
        PlaceDTO dto = new PlaceDTO(2L, "Mirante de Joinville do Boa Vista", "Mirante", "Joinville", "SC", createDateTimeString(), null);
        String json = DTOtoJSON(dto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PLACES_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        when(service.update(any(PlaceDTO.class), any(Long.class))).thenThrow(ObjectNotFoundException.class);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private String DTOtoJSON(PlaceDTO dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = mapper.writeValueAsString(dto);
        return json;
    }

}