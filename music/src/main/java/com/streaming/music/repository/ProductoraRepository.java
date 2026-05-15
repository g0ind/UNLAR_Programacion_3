package com.streaming.music.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.streaming.music.model.Productora;

@Repository
public class ProductoraRepository {
    private final Map<UUID, Productora> productoras = new ConcurrentHashMap<>();
    
    public Productora save(Productora productora) {
        productoras.put(productora.getId(), productora);
        return productora;
    }
    
    public Optional<Productora> findById(UUID id) {
        return Optional.ofNullable(productoras.get(id));
    }
    
    public List<Productora> findAll() {
        return new ArrayList<>(productoras.values());
    }
    
    public void deleteById(UUID id) {
        productoras.remove(id);
    }
    
    public boolean existsById(UUID id) {
        return productoras.containsKey(id);
    }

    public void update(Productora productora) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}