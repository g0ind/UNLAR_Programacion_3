package com.streaming.music.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Géneros musicales disponibles")
public enum Genero {
    ROCK("Rock"),
    POP("Pop"),
    JAZZ("Jazz"),
    ELECTRONICA("Electrónica"),
    CLASICA("Clásica");
    
    private final String displayName;
    
    Genero(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}