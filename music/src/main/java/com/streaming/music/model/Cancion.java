package com.streaming.music.model;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Cancion implements Comparable<Cancion> {
    private final UUID id;
    private String titulo;
    private UUID artistaId;
    private UUID albumId;
    private Genero genero;
    private int duracionSegundos;
    private final AtomicInteger reproducciones;
    private double rating;
    private LocalDate fechaLanzamiento;
    
    public Cancion(String titulo, UUID artistaId, UUID albumId, Genero genero, 
                   int duracionSegundos, double rating, LocalDate fechaLanzamiento) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.artistaId = artistaId;
        this.albumId = albumId;
        this.genero = genero;
        this.duracionSegundos = duracionSegundos;
        this.reproducciones = new AtomicInteger(0);
        this.rating = rating;
        this.fechaLanzamiento = fechaLanzamiento;
    }
    
    // Getters
    public UUID getId() { return id; }
    public String getTitulo() { return titulo; }
    public UUID getArtistaId() { return artistaId; }
    public UUID getAlbumId() { return albumId; }
    public Genero getGenero() { return genero; }
    public int getDuracionSegundos() { return duracionSegundos; }
    public int getReproducciones() { return reproducciones.get(); }
    public double getRating() { return rating; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    
    // Setters
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setGenero(Genero genero) { this.genero = genero; }
    public void setRating(double rating) { this.rating = rating; }
    
    // Método thread-safe para incrementar reproducciones
    public int incrementarReproducciones() {
        return reproducciones.incrementAndGet();
    }
    
    @Override
    public int compareTo(Cancion o) {
        return this.titulo.compareTo(o.titulo);
    }
    
    @Override
    public String toString() {
        return String.format("Cancion{id='%s', titulo='%s', reproducciones=%d}", 
                id, titulo, reproducciones.get());
    }
}