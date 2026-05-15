package com.streaming.music.repository;

import com.streaming.music.model.Album;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AlbumRepository {
    private final Map<UUID, Album> albumes = new ConcurrentHashMap<>();
    
    public Album save(Album album) {
        albumes.put(album.getId(), album);
        return album;
    }
    
    public Optional<Album> findById(UUID id) {
        return Optional.ofNullable(albumes.get(id));
    }
    
    public List<Album> findAll() {
        return new ArrayList<>(albumes.values());
    }
    
    public void deleteById(UUID id) {
        albumes.remove(id);
    }
    
    public boolean existsById(UUID id) {
        return albumes.containsKey(id);
    }
    
    public List<Album> findByArtistaId(UUID artistaId) {
        return albumes.values().stream()
                .filter(a -> a.getArtistaId().equals(artistaId))
                .toList();
    }
}