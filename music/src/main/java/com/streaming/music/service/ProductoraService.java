package com.streaming.music.service;

import com.streaming.music.dto.ProductoraDTO;
import com.streaming.music.exception.ResourceNotFoundException;
import com.streaming.music.model.Productora;
import com.streaming.music.repository.AlbumRepository;
import com.streaming.music.repository.ProductoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoraService {

    private final ProductoraRepository productoraRepository;
    private final AlbumRepository albumRepository;

    public List<ProductoraDTO> listarTodas() {
        return productoraRepository.findAll().stream()
                .map(p -> ProductoraDTO.fromProductora(p, 
                    (int) p.getAlbumesIds().stream()
                        .filter(albumRepository::existsById)
                        .count()))
                .toList();
    }

    public ProductoraDTO buscarPorId(UUID id) {
        Productora productora = productoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Productora no encontrada con ID: " + id));
        
        int cantidadAlbumes = (int) productora.getAlbumesIds().stream()
                .filter(albumRepository::existsById)
                .count();
        
        return ProductoraDTO.fromProductora(productora, cantidadAlbumes);
    }

    public List<ProductoraDTO> buscarPorNombre(String nombre) {
        return productoraRepository.findAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .map(p -> ProductoraDTO.fromProductora(p, 
                    (int) p.getAlbumesIds().stream()
                        .filter(albumRepository::existsById)
                        .count()))
                .toList();
    }

    public ProductoraDTO crearProductora(String nombre, String pais, Integer anioFundacion) {
        Productora productora = new Productora(nombre, pais, anioFundacion);
        Productora saved = productoraRepository.save(productora);
        return ProductoraDTO.fromProductora(saved, 0);
    }

    public ProductoraDTO actualizarProductora(UUID id, String nombre, String pais, Integer anioFundacion) {
        Productora productora = productoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Productora no encontrada con ID: " + id));
        
        if (nombre != null) productora.setNombre(nombre);
        if (pais != null) productora.setPais(pais);
        if (anioFundacion != null) productora.setAnioFundacion(anioFundacion);
        
        productoraRepository.update(productora);
        
        int cantidadAlbumes = (int) productora.getAlbumesIds().stream()
                .filter(albumRepository::existsById)
                .count();
        
        return ProductoraDTO.fromProductora(productora, cantidadAlbumes);
    }

    public void eliminarProductora(UUID id) {
        if (!productoraRepository.existsById(id)) {
            throw new ResourceNotFoundException("Productora no encontrada con ID: " + id);
        }
        productoraRepository.deleteById(id);
    }
}