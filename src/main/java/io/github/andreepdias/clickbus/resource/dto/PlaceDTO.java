package io.github.andreepdias.clickbus.resource.dto;

import java.time.LocalDateTime;

public class PlaceDTO {

    private Long id;
    private String name;
    private String slang;
    private String city;
    private String state;
    private String createdAt;
    private String updatedAt;

    public PlaceDTO(Long id, String name, String slang, String city, String state, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.slang = slang;
        this.city = city;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PlaceDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlang() {
        return slang;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
