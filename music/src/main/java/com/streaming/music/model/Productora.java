package com.streaming.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Productora {
    private final UUID id;
    private String nombre;
    private String pais;
    private Integer anioFundacion;
    private List<UUID> albumesIds;
    
    public Productora(String nombre, String pais, Integer anioFundacion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.pais = pais;
        this.anioFundacion = anioFundacion;
        this.albumesIds = new ArrayList<>();
    }
    
    // Getters
    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPais() { return pais; }
    public Integer getAnioFundacion() { return anioFundacion; }
    public List<UUID> getAlbumesIds() { return albumesIds; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPais(String pais) { this.pais = pais; }
    public void setAnioFundacion(Integer anioFundacion) { this.anioFundacion = anioFundacion; }
    public void agregarAlbum(UUID albumId) { this.albumesIds.add(albumId); }
}