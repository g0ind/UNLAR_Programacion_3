package com.streaming.music.service;

import com.streaming.music.dto.AlbumDTO;
import com.streaming.music.exception.ResourceNotFoundException;
import com.streaming.music.model.Album;
import com.streaming.music.model.Artista;
import com.streaming.music.model.Productora;
import com.streaming.music.repository.AlbumRepository;
import com.streaming.music.repository.ArtistaRepository;
import com.streaming.music.repository.CancionRepository;
import com.streaming.music.repository.ProductoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;
    private final ProductoraRepository productoraRepository;
    private final CancionRepository cancionRepository;

    public List<AlbumDTO> listarTodos() {
        return albumRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public AlbumDTO buscarPorId(UUID id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con ID: " + id));
        return toDTO(album);
    }

    public List<AlbumDTO> buscarPorTitulo(String titulo) {
        return albumRepository.findAll().stream()
                .filter(a -> a.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .map(this::toDTO)
                .toList();
    }

    public AlbumDTO crearAlbum(String titulo, UUID artistaId, UUID productoraId, LocalDate fechaLanzamiento) {
        // Validar que el artista existe
        Artista artista = artistaRepository.findById(artistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con ID: " + artistaId));
        
        // Validar que la productora existe
        Productora productora = productoraRepository.findById(productoraId)
                .orElseThrow(() -> new ResourceNotFoundException("Productora no encontrada con ID: " + productoraId));
        
        Album album = new Album(titulo, artistaId, productoraId, fechaLanzamiento);
        Album saved = albumRepository.save(album);
        
        // Asociar el álbum al artista y productora
        artista.agregarAlbum(saved.getId());
        productora.agregarAlbum(saved.getId());
        
        return toDTO(saved);
    }

    public AlbumDTO actualizarAlbum(UUID id, String titulo, LocalDate fechaLanzamiento) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con ID: " + id));
        
        if (titulo != null) album.setTitulo(titulo);
        if (fechaLanzamiento != null) album.setFechaLanzamiento(fechaLanzamiento);
        
        albumRepository.update(album);
        return toDTO(album);
    }

    public void eliminarAlbum(UUID id) {
        if (!albumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Álbum no encontrado con ID: " + id);
        }
        albumRepository.deleteById(id);
    }

    private AlbumDTO toDTO(Album album) {
        String artistaNombre = artistaRepository.findById(album.getArtistaId())
                .map(Artista::getNombre)
                .orElse("Desconocido");
        
        String productoraNombre = productoraRepository.findById(album.getProductoraId())
                .map(Productora::getNombre)
                .orElse("Desconocido");
        
        int cantidadCanciones = (int) album.getCancionesIds().stream()
                .filter(cancionRepository::existsById)
                .count();
        
        return AlbumDTO.fromAlbum(album, artistaNombre, productoraNombre, cantidadCanciones);
    }
}