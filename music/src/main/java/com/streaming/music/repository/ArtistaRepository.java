package com.streaming.music.repository;

import com.streaming.music.model.Artista;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArtistaRepository {
    private final Map<UUID, Artista> artistas = new ConcurrentHashMap<>();
    
    public Artista save(Artista artista) {
        artistas.put(artista.getId(), artista);
        return artista;
    }
    
    public Optional<Artista> findById(UUID id) {
        return Optional.ofNullable(artistas.get(id));
    }
    
    public List<Artista> findAll() {
        return new ArrayList<>(artistas.values());
    }
    
    public void deleteById(UUID id) {
        artistas.remove(id);
    }
    
    public boolean existsById(UUID id) {
        return artistas.containsKey(id);
    }
    
    public Optional<Artista> findByNombre(String nombre) {
        return artistas.values().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
}