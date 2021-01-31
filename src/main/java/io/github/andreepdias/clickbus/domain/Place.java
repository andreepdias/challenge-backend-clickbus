package io.github.andreepdias.clickbus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Clock;
import java.time.LocalDateTime;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slang;
    private String city;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Place(Long id, String name, String slang, String city, String state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slang = slang;
        this.city = city;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Place() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlang(String slang) {
        this.slang = slang;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void updateCreationMoment(Clock clock){
        this.createdAt = LocalDateTime.now(clock);
    }

    public void updateAtualizationMoment(Clock clock){
        this.updatedAt = LocalDateTime.now(clock);
    }
}
