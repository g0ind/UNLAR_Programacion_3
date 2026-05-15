package com.streaming.music.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Album {
    private final UUID id;
    private String titulo;
    private UUID artistaId;
    private UUID productoraId;
    private LocalDate fechaLanzamiento;
    private List<UUID> cancionesIds;
    
    public Album(String titulo, UUID artistaId, UUID productoraId, LocalDate fechaLanzamiento) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.artistaId = artistaId;
        this.productoraId = productoraId;
        this.fechaLanzamiento = fechaLanzamiento;
        this.cancionesIds = new ArrayList<>();
    }
    
    // Getters
    public UUID getId() { return id; }
    public String getTitulo() { return titulo; }
    public UUID getArtistaId() { return artistaId; }
    public UUID getProductoraId() { return productoraId; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public List<UUID> getCancionesIds() { return cancionesIds; }
    
    // Setters
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
    public void agregarCancion(UUID cancionId) { this.cancionesIds.add(cancionId); }
}