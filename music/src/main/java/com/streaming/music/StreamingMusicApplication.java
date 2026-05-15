package com.streaming.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamingMusicApplication {  // ← Este nombre debe coincidir con el archivo
    
    public static void main(String[] args) {
        SpringApplication.run(StreamingMusicApplication.class, args);
        
        System.out.println("""
                
                ╔══════════════════════════════════════════════════════════════╗
                ║     🎵 STREAMING MUSIC API - INICIADA CORRECTAMENTE 🎵        ║
                ╠══════════════════════════════════════════════════════════════╣
                ║  Swagger UI: http://localhost:8080/swagger-ui.html          ║
                ║  OpenAPI JSON: http://localhost:8080/api-docs               ║
                ╚══════════════════════════════════════════════════════════════╝
                """);
    }
}