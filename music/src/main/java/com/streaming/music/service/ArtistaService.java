package com.streaming.music.service;

import com.streaming.music.dto.ArtistaDTO;
import com.streaming.music.exception.ResourceNotFoundException;
import com.streaming.music.model.Artista;
import com.streaming.music.repository.AlbumRepository;
import com.streaming.music.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistaService {
    
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    
    public List<ArtistaDTO> listarTodos() {
        return artistaRepository.findAll().stream()
                .map(a -> ArtistaDTO.fromArtista(a, 
                    albumRepository.findByArtistaId(a.getId()).size()))
                .toList();
    }
    
    public ArtistaDTO buscarPorId(UUID id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con ID: " + id));
        
        int cantidadAlbumes = albumRepository.findByArtistaId(id).size();
        return ArtistaDTO.fromArtista(artista, cantidadAlbumes);
    }
    
    public ArtistaDTO crearArtista(String nombre, String pais, Integer anioFormacion) {
        Artista artista = new Artista(nombre, pais, anioFormacion);
        Artista saved = artistaRepository.save(artista);
        return ArtistaDTO.fromArtista(saved, 0);
    }
    
    public ArtistaDTO actualizarArtista(UUID id, String nombre, String pais, Integer anioFormacion) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con ID: " + id));
        
        if (nombre != null) artista.setNombre(nombre);
        if (pais != null) artista.setPais(pais);
        if (anioFormacion != null) artista.setAnioFormacion(anioFormacion);
        
        artistaRepository.update(artista);
        
        int cantidadAlbumes = albumRepository.findByArtistaId(id).size();
        return ArtistaDTO.fromArtista(artista, cantidadAlbumes);
    }
    
    public void eliminarArtista(UUID id) {
        if (!artistaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Artista no encontrado con ID: " + id);
        }
        artistaRepository.deleteById(id);
    }
    
    public List<ArtistaDTO> buscarPorNombre(String nombre) {
        return artistaRepository.findAll().stream()
                .filter(a -> a.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .map(a -> ArtistaDTO.fromArtista(a, 
                    albumRepository.findByArtistaId(a.getId()).size()))
                .toList();
    }
}