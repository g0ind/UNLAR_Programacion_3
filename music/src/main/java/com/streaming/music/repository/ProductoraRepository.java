package com.streaming.music.repository;

import com.streaming.music.model.Productora;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
}