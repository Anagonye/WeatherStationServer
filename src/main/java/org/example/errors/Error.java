package org.example.errors;

import java.time.LocalDateTime;

public class Error {

    private String type;
    private String description;
    private final LocalDateTime savedAt;



    public Error(String type, String description) {
        this.type = type;
        this.description = description;
        this.savedAt = LocalDateTime.now();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }
}
