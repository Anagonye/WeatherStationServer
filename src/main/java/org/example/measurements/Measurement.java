package org.example.measurements;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public abstract class Measurement {

    private Long id;

    @JsonIgnore
    private final LocalDateTime createdAt;

    public Measurement(){
        createdAt = LocalDateTime.now();
    }

    public Measurement(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Measurement(Long id, LocalDateTime createdAt){
        this.id = id;
        this.createdAt = createdAt;
    }

    public LocalDateTime getSaveAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
