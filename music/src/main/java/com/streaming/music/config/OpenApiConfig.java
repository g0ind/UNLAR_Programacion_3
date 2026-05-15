package com.streaming.music.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.models.GroupedOpenApi;
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
                                
                                #### 📊 Complejidad Big O:
                                | Algoritmo | Complejidad | Descripción |
                                |-----------|-------------|-------------|
                                | Búsqueda Binaria | O(log n) | Búsqueda por título en lista ordenada |
                                | Búsqueda Lineal | O(n) | Filtros compuestos múltiples |
                                | Playlist Mochila | O(2^n) | Subset sum recursivo (peor caso) |
                                | Streams filter | O(n) | Filtrado con Stream API |
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
                                .description("Servidor de Producción")
                ))
                .tags(List.of(
                        new Tag().name("Canciones").description("Operaciones CRUD y búsqueda de canciones - Incluye filtros Stream API, algoritmos de búsqueda y recomendaciones"),
                        new Tag().name("Artistas").description("Gestión de artistas - CRUD completo y búsqueda por nombre"),
                        new Tag().name("Álbumes").description("Gestión de álbumes - CRUD completo y relación con artistas/productoras"),
                        new Tag().name("Productoras").description("Gestión de productoras/discográficas - CRUD completo"),
                        new Tag().name("Estadísticas").description("Estadísticas avanzadas con Streams API - Promedios, distribuciones y rankings")
                ))
                .components(new Components()
                        .addSchemas("CancionDTO", new Schema<>()
                                .type("object")
                                .addProperty("id", new StringSchema().example("123e4567-e89b-12d3-a456-426614174000"))
                                .addProperty("titulo", new StringSchema().example("Bohemian Rhapsody"))
                                .addProperty("artistaNombre", new StringSchema().example("Queen"))
                                .addProperty("albumTitulo", new StringSchema().example("A Night at the Opera"))
                                .addProperty("genero", new StringSchema().example("ROCK"))
                                .addProperty("duracionSegundos", new Schema<>().type("integer").example(355))
                                .addProperty("duracionMinutos", new Schema<>().type("integer").example(5))
                                .addProperty("reproducciones", new Schema<>().type("integer").example(1000))
                                .addProperty("rating", new Schema<>().type("number").example(4.9))
                                .addProperty("anio", new Schema<>().type("integer").example(1975)))
                        .addSchemas("ArtistaDTO", new Schema<>()
                                .type("object")
                                .addProperty("id", new StringSchema().example("123e4567-e89b-12d3-a456-426614174001"))
                                .addProperty("nombre", new StringSchema().example("Queen"))
                                .addProperty("pais", new StringSchema().example("Reino Unido"))
                                .addProperty("anioFormacion", new Schema<>().type("integer").example(1970))
                                .addProperty("cantidadAlbumes", new Schema<>().type("integer").example(15)))
                        .addSchemas("AlbumDTO", new Schema<>()
                                .type("object")
                                .addProperty("id", new StringSchema().example("123e4567-e89b-12d3-a456-426614174002"))
                                .addProperty("titulo", new StringSchema().example("A Night at the Opera"))
                                .addProperty("artistaNombre", new StringSchema().example("Queen"))
                                .addProperty("productoraNombre", new StringSchema().example("EMI Records"))
                                .addProperty("fechaLanzamiento", new StringSchema().example("1975-11-21"))
                                .addProperty("cantidadCanciones", new Schema<>().type("integer").example(12)))
                        .addSchemas("ProductoraDTO", new Schema<>()
                                .type("object")
                                .addProperty("id", new StringSchema().example("123e4567-e89b-12d3-a456-426614174003"))
                                .addProperty("nombre", new StringSchema().example("EMI Records"))
                                .addProperty("pais", new StringSchema().example("Reino Unido"))
                                .addProperty("anioFundacion", new Schema<>().type("integer").example(1931))
                                .addProperty("cantidadAlbumes", new Schema<>().type("integer").example(50)))
                        .addSchemas("ErrorResponse", new Schema<>()
                                .type("object")
                                .addProperty("error", new StringSchema().example("Canción no encontrada con ID: 123")))
                        .addSchemas("PlaylistResponse", new Schema<>()
                                .type("object")
                                .addProperty("canciones", new Schema<>().type("array").items(new Schema<>().$ref("#/components/schemas/CancionDTO")))
                                .addProperty("duracionTotalMinutos", new Schema<>().type("number").example(15.5))
                                .addProperty("duracionObjetivo", new Schema<>().type("integer").example(15))
                                .addProperty("cantidadCanciones", new Schema<>().type("integer").example(3)))
                );
    }

    /**
     * Agrupación de APIs para Canciones
     */
    @Bean
    public GroupedOpenApi cancionesApi() {
        return GroupedOpenApi.builder()
                .group("canciones")
                .displayName("🎵 Canciones API")
                .pathsToMatch("/api/canciones/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.getInfo().setDescription("""
                            ## API de Canciones
                            
                            ### Endpoints disponibles:
                            
                            #### CRUD Básico
                            - `GET /api/canciones` - Listar todas las canciones
                            - `GET /api/canciones/{id}` - Buscar por UUID
                            - `POST /api/canciones` - Crear nueva canción
                            - `DELETE /api/canciones/{id}` - Eliminar canción
                            - `POST /api/canciones/{id}/reproducir` - Incrementar reproducciones (thread-safe)
                            
                            #### Búsqueda y Filtros (Streams API)
                            - `GET /api/canciones/buscar` - Filtro compuesto (género, rating, duración)
                            - `GET /api/canciones/top10` - Top 10 más reproducidas
                            
                            #### Algoritmos (Análisis Big O)
                            - `GET /api/canciones/busqueda-lineal` - Búsqueda lineal O(n) con múltiples predicados
                            - `GET /api/canciones/busqueda-binaria` - Búsqueda binaria O(log n) por título
                            - `GET /api/canciones/ordenar` - Ordenamiento personalizado O(n log n)
                            
                            #### Playlist Automática
                            - `GET /api/canciones/playlist` - Subset sum recursivo O(2^n) - duración exacta
                            
                            #### Patrón Strategy - Recomendaciones
                            - `GET /api/canciones/{id}/recomendaciones/genero` - Estrategia por género
                            - `GET /api/canciones/recomendaciones/top5` - Estrategia por popularidad
                            - `GET /api/canciones/{id}/recomendaciones/descubrimiento` - Estrategia descubrimiento
                            
                            #### Estadísticas (Streams API)
                            - `GET /api/canciones/estadisticas/promedio-duracion` - Collectors.averagingInt
                            - `GET /api/canciones/estadisticas/artista-popular` - Collectors.maxBy
                            - `GET /api/canciones/estadisticas/distribucion-decadas` - Collectors.groupingBy
                            """);
                })
                .build();
    }

    /**
     * Agrupación de APIs para Artistas
     */
    @Bean
    public GroupedOpenApi artistasApi() {
        return GroupedOpenApi.builder()
                .group("artistas")
                .displayName("👨‍🎤 Artistas API")
                .pathsToMatch("/api/artistas/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.getInfo().setDescription("""
                            ## API de Artistas
                            
                            ### Endpoints disponibles:
                            
                            - `GET /api/artistas` - Listar todos los artistas
                            - `GET /api/artistas/{id}` - Buscar artista por UUID
                            - `GET /api/artistas/buscar?nombre=xxx` - Búsqueda por nombre
                            - `POST /api/artistas` - Crear nuevo artista (201 Created)
                            - `PUT /api/artistas/{id}` - Actualizar artista existente
                            - `DELETE /api/artistas/{id}` - Eliminar artista (204 No Content)
                            """);
                })
                .build();
    }

    /**
     * Agrupación de APIs para Álbumes
     */
    @Bean
    public GroupedOpenApi albumesApi() {
        return GroupedOpenApi.builder()
                .group("albumes")
                .displayName("💿 Álbumes API")
                .pathsToMatch("/api/albumes/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.getInfo().setDescription("""
                            ## API de Álbumes
                            
                            ### Endpoints disponibles:
                            
                            - `GET /api/albumes` - Listar todos los álbumes
                            - `GET /api/albumes/{id}` - Buscar álbum por UUID
                            - `GET /api/albumes/buscar?titulo=xxx` - Búsqueda por título
                            - `POST /api/albumes` - Crear nuevo álbum (valida artista y productora)
                            - `PUT /api/albumes/{id}` - Actualizar álbum
                            - `DELETE /api/albumes/{id}` - Eliminar álbum
                            """);
                })
                .build();
    }

    /**
     * Agrupación de APIs para Productoras
     */
    @Bean
    public GroupedOpenApi productorasApi() {
        return GroupedOpenApi.builder()
                .group("productoras")
                .displayName("🏢 Productoras API")
                .pathsToMatch("/api/productoras/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.getInfo().setDescription("""
                            ## API de Productoras/Discográficas
                            
                            ### Endpoints disponibles:
                            
                            - `GET /api/productoras` - Listar todas las productoras
                            - `GET /api/productoras/{id}` - Buscar productora por UUID
                            - `GET /api/productoras/buscar?nombre=xxx` - Búsqueda por nombre
                            - `POST /api/productoras` - Crear nueva productora
                            - `PUT /api/productoras/{id}` - Actualizar productora
                            - `DELETE /api/productoras/{id}` - Eliminar productora
                            """);
                })
                .build();
    }

    /**
     * Agrupación de APIs para Estadísticas
     */
    @Bean
    public GroupedOpenApi estadisticasApi() {
        return GroupedOpenApi.builder()
                .group("estadisticas")
                .displayName("📊 Estadísticas API")
                .pathsToMatch("/api/estadisticas/**", "/api/canciones/estadisticas/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.getInfo().setDescription("""
                            ## API de Estadísticas Avanzadas
                            
                            ### Endpoints disponibles:
                            
                            - `GET /api/canciones/estadisticas/promedio-duracion` - Promedio duración por género (Collectors.averagingInt)
                            - `GET /api/canciones/estadisticas/artista-popular` - Artista más popular (Collectors.maxBy)
                            - `GET /api/canciones/estadisticas/distribucion-decadas` - Distribución por décadas (Collectors.groupingBy)
                            """);
                })
                .build();
    }
}