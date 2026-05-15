package com.streaming.music.repository;

import com.streaming.music.model.Cancion;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CancionRepository {
    private final Map<UUID, Cancion> canciones = new ConcurrentHashMap<>();
    
    public Cancion save(Cancion cancion) {
        canciones.put(cancion.getId(), cancion);
        return cancion;
    }
    
    public Optional<Cancion> findById(UUID id) {
        return Optional.ofNullable(canciones.get(id));
    }
    
    public List<Cancion> findAll() {
        return new ArrayList<>(canciones.values());
    }
    
    public void deleteById(UUID id) {
        canciones.remove(id);
    }
    
    public boolean existsById(UUID id) {
        return canciones.containsKey(id);
    }
    
    public void update(Cancion cancion) {
        canciones.put(cancion.getId(), cancion);
    }
    
    public void clear() {
        canciones.clear();
    }
}