package com.streaming.music.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("🎵 Streaming Music API - Plataforma de Streaming de Música")
                                                .description("""
                                                                ## Backend de Plataforma de Streaming de Música

                                                                ### UNIDAD 2: ALGORITMOS FUNDAMENTALES

                                                                API desarrollada para la gestión de canciones, creación de playlists automáticas
                                                                y generación de recomendaciones, aplicando programación funcional y concurrente.

                                                                ### Funcionalidades Implementadas:

                                                                #### 📀 Streams API (30%)
                                                                - Filtrado compuesto (género, rating, duración)
                                                                - Top 10 más reproducidas
                                                                - Estadísticas: promedio duración por género, artista más popular, distribución por décadas
                                                                - Playlist automática con algoritmo de subset sum (mochila recursiva)

                                                                #### 🔍 Algoritmos de Búsqueda y Ordenamiento (20%)
                                                                - Búsqueda binaria por título (O(log n))
                                                                - Búsqueda lineal con predicados múltiples (O(n))
                                                                - Ordenamiento personalizado: artista + fecha de lanzamiento descendente

                                                                #### 🎯 Patrón Strategy (20%)
                                                                - Recomendación por género
                                                                - Recomendación por popularidad (top 5 global)
                                                                - Recomendación descubrimiento (<1000 plays, <2 años, género diferente)

                                                                #### 🚀 Concurrencia
                                                                - Virtual Threads (Java 21)
                                                                - AtomicInteger para contadores thread-safe
                                                                - Test de concurrencia con 100 hilos virtuales
                                                                """)
                                                .version("1.0.0")
                                                .contact(new Contact()
                                                                .name("Streaming Music Team")
                                                                .email("soporte@streaming-music.com")
                                                                .url("https://github.com/streaming-music"))
                                                .license(new License()
                                                                .name("MIT License")
                                                                .url("https://opensource.org/licenses/MIT")))
                                .servers(List.of(
                                                new Server()
                                                                .url("http://localhost:8080")
                                                                .description("Servidor Local de Desarrollo"),
                                                new Server()
                                                                .url("https://api.streaming-music.com")
                                                                .description("Servidor de Producción")))
                                .tags(List.of(
                                                new Tag().name("Canciones").description(
                                                                "Operaciones CRUD y búsqueda de canciones"),
                                                new Tag().name("Artistas")
                                                                .description("Gestión de artistas - CRUD completo"),
                                                new Tag().name("Álbumes").description("Gestión de álbumes"),
                                                new Tag().name("Productoras")
                                                                .description("Gestión de productoras/discográficas"),
                                                new Tag().name("Estadísticas")
                                                                .description("Estadísticas avanzadas con Streams API")))
                                .components(new Components()
                                                .addSchemas("CancionDTO", new Schema<>()
                                                                .type("object")
                                                                .addProperty("id", new StringSchema().example(
                                                                                "123e4567-e89b-12d3-a456-426614174000"))
                                                                .addProperty("titulo",
                                                                                new StringSchema().example(
                                                                                                "Bohemian Rhapsody"))
                                                                .addProperty("artistaNombre",
                                                                                new StringSchema().example("Queen"))
                                                                .addProperty("duracionSegundos",
                                                                                new Schema<>().type("integer")
                                                                                                .example(355))
                                                                .addProperty("rating", new Schema<>().type("number")
                                                                                .example(4.9))));
        }
}