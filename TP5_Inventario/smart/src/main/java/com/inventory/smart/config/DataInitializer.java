package com.inventory.smart.config;

import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos de prueba para el almacén de inventario.
 *
 * <p>Crea categorías y productos por defecto durante el arranque de la aplicación.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    /**
     * Inyección por constructor de los repositorios.
     *
     * @param categoriaRepository el repositorio de categorías
     * @param productoRepository  el repositorio de productos
     */
    public DataInitializer(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Se ejecuta tras el inicio de la aplicación para sembrar el almacenamiento en memoria.
     *
     * @param args argumentos de línea de comandos
     * @throws Exception en caso de errores en la inicialización
     */
    @Override
    public void run(String... args) throws Exception {
        // Inicializar categorías
        Categoria electronicos = categoriaRepository.save(new Categoria(null, "Electrónicos"));
        Categoria alimentos = categoriaRepository.save(new Categoria(null, "Alimentos"));
        Categoria limpieza = categoriaRepository.save(new Categoria(null, "Limpieza"));

        // Inicializar productos
        productoRepository.save(new Producto(null, "Notebook Dell XPS 15", "Laptop de alto rendimiento", 1599.99, 25, electronicos));
        productoRepository.save(new Producto(null, "Smart TV LG 55", "Televisor inteligente 4K", 649.99, 8, electronicos)); // stock < 10 (alerta bajo)
        productoRepository.save(new Producto(null, "Arroz Gallo Oro 1kg", "Arroz parboilizado de primera calidad", 2.50, 150, alimentos));
        productoRepository.save(new Producto(null, "Leche Entera La Serenísima 1L", "Leche fluida pasteurizada", 1.80, 2, alimentos)); // stock < 3 (alerta crítico)
        productoRepository.save(new Producto(null, "Detergente Ala 500ml", "Detergente lavavajillas ultra concentrado", 1.20, 40, limpieza));
    }
}
