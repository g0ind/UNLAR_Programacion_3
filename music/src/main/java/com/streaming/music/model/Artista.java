package com.streaming.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Artista {
    private final UUID id;
    private String nombre;
    private String pais;
    private Integer anioFormacion;
    private List<UUID> albumesIds;
    
    public Artista(String nombre, String pais, Integer anioFormacion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.pais = pais;
        this.anioFormacion = anioFormacion;
        this.albumesIds = new ArrayList<>();
    }
    
    // Getters
    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPais() { return pais; }
    public Integer getAnioFormacion() { return anioFormacion; }
    public List<UUID> getAlbumesIds() { return albumesIds; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPais(String pais) { this.pais = pais; }
    public void setAnioFormacion(Integer anioFormacion) { this.anioFormacion = anioFormacion; }
    public void agregarAlbum(UUID albumId) { this.albumesIds.add(albumId); }
}