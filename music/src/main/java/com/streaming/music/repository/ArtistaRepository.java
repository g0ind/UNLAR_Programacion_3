package com.streaming.music.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.streaming.music.model.Artista;

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
    
    // ✅ Este es el método update que falta
    public Artista update(Artista artista) {
        return save(artista);
    }
    
    public Optional<Artista> findByNombre(String nombre) {
        return artistas.values().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
    
    public void clear() {
        artistas.clear();
    }
}