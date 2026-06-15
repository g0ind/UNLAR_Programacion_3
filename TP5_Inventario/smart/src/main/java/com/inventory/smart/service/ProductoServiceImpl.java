package com.inventory.smart.service;

import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.dto.ProductoResponse;
import com.inventory.smart.exception.BusinessRuleException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementación de {@link ProductoService} para la gestión de lógica de productos.
 *
 * <p>Aplica reglas de negocio de creación y manipulación de productos e interactúa con
 * los repositorios correspondientes.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Inyección por constructor de las dependencias requeridas.
     *
     * @param productoRepository  el repositorio de productos
     * @param categoriaRepository el repositorio de categorías
     */
    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Lista todos los productos aplicando filtros opcionales.
     *
     * @param categoriaId identificador de la categoría a filtrar (opcional)
     * @param precioMin   precio mínimo a filtrar (opcional)
     * @param precioMax   precio máximo a filtrar (opcional)
     * @param enStock     si es verdadero, filtra productos que tengan stock > 0 (opcional)
     * @return lista de productos que coinciden con los criterios indicados
     */
    @Override
    public List<ProductoResponse> findAll(Long categoriaId, Double precioMin, Double precioMax, Boolean enStock) {
        return productoRepository.findAll().stream()
                .filter(p -> categoriaId == null || (p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId)))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .filter(p -> enStock == null || !enStock || p.getStock() > 0)
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Busca un producto por su identificador único.
     *
     * @param id identificador del producto
     * @return el producto encontrado mapeado a DTO
     * @throws ResourceNotFoundException si el producto no existe
     */
    @Override
    public ProductoResponse findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return mapToResponse(producto);
    }

    /**
     * Crea un nuevo producto en el catálogo.
     *
     * @param request los datos del producto a crear
     * @return el producto creado mapeado a DTO
     * @throws BusinessRuleException si el stock inicial es menor a 0
     * @throws ResourceNotFoundException si la categoría indicada no existe
     */
    @Override
    public ProductoResponse crear(ProductoRequest request) {
        if (request.stockInicial() < 0) {
            throw new BusinessRuleException("El stock inicial debe ser mayor o igual a 0.");
        }
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.categoriaId()));

        Producto producto = new Producto(null, request.nombre(), request.descripcion(), request.precio(), request.stockInicial(), categoria);
        Producto guardado = productoRepository.save(producto);
        return mapToResponse(guardado);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id      identificador del producto a actualizar
     * @param request los nuevos datos del producto
     * @return el producto actualizado mapeado a DTO
     * @throws ResourceNotFoundException si el producto o la categoría no existen
     */
    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.categoriaId()));

        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setCategoria(categoria);
        
        Producto guardado = productoRepository.save(producto);
        return mapToResponse(guardado);
    }

    /**
     * Elimina un producto del catálogo por su ID.
     *
     * @param id el identificador del producto a eliminar
     * @throws ResourceNotFoundException si el producto no existe
     */
    @Override
    public void deleteById(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    /**
     * Realiza una búsqueda textual insensible a mayúsculas/minúsculas sobre el nombre de los productos.
     *
     * @param q texto de búsqueda (no puede estar vacío)
     * @return lista de productos que coinciden con el texto de búsqueda
     * @throws IllegalArgumentException si el parámetro q es nulo o vacío
     */
    @Override
    public List<ProductoResponse> buscarPorNombre(String q) {
        if (q == null || q.isBlank()) {
            throw new IllegalArgumentException("El parámetro de búsqueda 'q' no puede estar vacío.");
        }
        return productoRepository.buscarPorNombre(q).stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Devuelve los productos ordenados por un campo y dirección específicos.
     *
     * @param campo el campo de ordenación (nombre, precio o stock)
     * @param orden el sentido del ordenamiento (asc o desc)
     * @return lista de productos ordenados
     * @throws IllegalArgumentException si el campo o dirección no son válidos
     */
    @Override
    public List<ProductoResponse> listarOrdenados(String campo, String orden) {
        List<Producto> productos = new ArrayList<>(productoRepository.findAll());
        
        Comparator<Producto> comparator;
        if ("nombre".equalsIgnoreCase(campo)) {
            comparator = Comparator.comparing(Producto::getNombre, Comparator.nullsLast(String::compareToIgnoreCase));
        } else if ("precio".equalsIgnoreCase(campo)) {
            comparator = Comparator.comparingDouble(Producto::getPrecio);
        } else if ("stock".equalsIgnoreCase(campo)) {
            comparator = Comparator.comparingInt(Producto::getStock);
        } else {
            throw new IllegalArgumentException("Campo de ordenación no válido: " + campo);
        }

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        } else if (orden != null && !orden.isBlank() && !"asc".equalsIgnoreCase(orden)) {
            throw new IllegalArgumentException("Dirección de ordenación no válida: " + orden);
        }

        productos.sort(comparator);
        return productos.stream().map(this::mapToResponse).toList();
    }

    /**
     * Mapea un objeto {@link Producto} a su correspondiente DTO {@link ProductoResponse}.
     *
     * @param p el producto a mapear
     * @return el DTO de respuesta resultante
     */
    private ProductoResponse mapToResponse(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria() != null ? new CategoriaResponse(p.getCategoria().getId(), p.getCategoria().getNombre()) : null
        );
    }
}
