package com.streaming.music.config;

import com.streaming.music.model.*;
import com.streaming.music.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final ArtistaRepository artistaRepository;
    private final ProductoraRepository productoraRepository;
    private final AlbumRepository albumRepository;
    private final CancionRepository cancionRepository;
    
    @Override
    public void run(String... args) {
        // Limpiar datos existentes
        cancionRepository.clear();
        
        // Crear Artistas
        Artista queen = new Artista("Queen", "Reino Unido", 1970);
        Artista beatles = new Artista("The Beatles", "Reino Unido", 1960);
        Artista bocelli = new Artista("Andrea Bocelli", "Italia", 1992);
        
        artistaRepository.save(queen);
        artistaRepository.save(beatles);
        artistaRepository.save(bocelli);
        
        // Crear Productoras
        Productora emi = new Productora("EMI Records", "Reino Unido", 1931);
        Productora universal = new Productora("Universal Music", "EE.UU.", 1934);
        Productora sugar = new Productora("Sugar Music", "Italia", 1990);
        
        productoraRepository.save(emi);
        productoraRepository.save(universal);
        productoraRepository.save(sugar);
        
        // Crear Álbumes
        Album aNightAtTheOpera = new Album("A Night at the Opera", queen.getId(), emi.getId(), LocalDate.of(1975, 11, 21));
        Album abbeyRoad = new Album("Abbey Road", beatles.getId(), emi.getId(), LocalDate.of(1969, 9, 26));
        Album romanzo = new Album("Romanza", bocelli.getId(), sugar.getId(), LocalDate.of(1997, 1, 1));
        
        albumRepository.save(aNightAtTheOpera);
        albumRepository.save(abbeyRoad);
        albumRepository.save(romanzo);
        
        // Crear Canciones
        Cancion bohemian = new Cancion("Bohemian Rhapsody", queen.getId(), aNightAtTheOpera.getId(), 
                Genero.ROCK, 355, 4.9, LocalDate.of(1975, 10, 31));
        Cancion loveOfMyLife = new Cancion("Love of My Life", queen.getId(), aNightAtTheOpera.getId(), 
                Genero.ROCK, 219, 4.7, LocalDate.of(1975, 11, 21));
        Cancion hereComesTheSun = new Cancion("Here Comes the Sun", beatles.getId(), abbeyRoad.getId(), 
                Genero.POP, 185, 4.8, LocalDate.of(1969, 9, 26));
        Cancion comeTogether = new Cancion("Come Together", beatles.getId(), abbeyRoad.getId(), 
                Genero.ROCK, 259, 4.6, LocalDate.of(1969, 9, 26));
        Cancion timeToSayGoodbye = new Cancion("Time to Say Goodbye", bocelli.getId(), romanzo.getId(), 
                Genero.CLASICA, 247, 4.9, LocalDate.of(1995, 1, 1));
        Cancion conTePartiro = new Cancion("Con te partirò", bocelli.getId(), romanzo.getId(), 
                Genero.CLASICA, 250, 4.8, LocalDate.of(1995, 1, 1));
        
        // Incrementar algunas reproducciones para datos interesantes
        for (int i = 0; i < 1000; i++) bohemian.incrementarReproducciones();
        for (int i = 0; i < 800; i++) hereComesTheSun.incrementarReproducciones();
        for (int i = 0; i < 600; i++) timeToSayGoodbye.incrementarReproducciones();
        for (int i = 0; i < 400; i++) conTePartiro.incrementarReproducciones();
        for (int i = 0; i < 200; i++) comeTogether.incrementarReproducciones();
        
        cancionRepository.save(bohemian);
        cancionRepository.save(loveOfMyLife);
        cancionRepository.save(hereComesTheSun);
        cancionRepository.save(comeTogether);
        cancionRepository.save(timeToSayGoodbye);
        cancionRepository.save(conTePartiro);
        
        // Asociar canciones a álbumes
        aNightAtTheOpera.agregarCancion(bohemian.getId());
        aNightAtTheOpera.agregarCancion(loveOfMyLife.getId());
        abbeyRoad.agregarCancion(hereComesTheSun.getId());
        abbeyRoad.agregarCancion(comeTogether.getId());
        romanzo.agregarCancion(timeToSayGoodbye.getId());
        romanzo.agregarCancion(conTePartiro.getId());
        
        System.out.println("✅ Datos de ejemplo cargados exitosamente!");
        System.out.println("📀 Artistas: 3, Álbumes: 3, Canciones: 6");
    }
}