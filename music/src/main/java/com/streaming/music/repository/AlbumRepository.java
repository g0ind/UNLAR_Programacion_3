package com.streaming.music.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.streaming.music.model.Album;

@Repository
public class AlbumRepository {
    
    private final Map<UUID, Album> albumes = new ConcurrentHashMap<>();
    
    // Crear o actualizar
    public Album save(Album album) {
        albumes.put(album.getId(), album);
        return album;
    }
    
    // Buscar por ID
    public Optional<Album> findById(UUID id) {
        return Optional.ofNullable(albumes.get(id));
    }
    
    // Listar todos
    public List<Album> findAll() {
        return new ArrayList<>(albumes.values());
    }
    
    // Eliminar por ID
    public void deleteById(UUID id) {
        albumes.remove(id);
    }
    
    // Verificar si existe
    public boolean existsById(UUID id) {
        return albumes.containsKey(id);
    }
    
    // Actualizar (sinónimo de save)
    public Album update(Album album) {
        return save(album);  // Reutiliza save
    }
    
    // Buscar por artista
    public List<Album> findByArtistaId(UUID artistaId) {
        return albumes.values().stream()
                .filter(album -> album.getArtistaId().equals(artistaId))
                .toList();
    }
    
    // Buscar por productora
    public List<Album> findByProductoraId(UUID productoraId) {
        return albumes.values().stream()
                .filter(album -> album.getProductoraId().equals(productoraId))
                .toList();
    }
    
    // Limpiar todos (para tests)
    public void clear() {
        albumes.clear();
    }
    
    // Contar álbumes
    public long count() {
        return albumes.size();
    }
}