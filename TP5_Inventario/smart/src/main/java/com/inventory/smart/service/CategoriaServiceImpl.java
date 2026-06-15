package com.inventory.smart.service;

import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.exception.BusinessRuleException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementación de {@link CategoriaService} para gestionar las categorías.
 *
 * <p>Controla las reglas de integridad referencial antes de permitir eliminaciones.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    /**
     * Inyección por constructor de las dependencias requeridas.
     *
     * @param categoriaRepository el repositorio de categorías
     * @param productoRepository  el repositorio de productos (para validación de integridad)
     */
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Recupera todas las categorías registradas.
     *
     * @return lista de todas las categorías
     */
    @Override
    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Busca una categoría por su identificador único.
     *
     * @param id el identificador de la categoría
     * @return la categoría encontrada mapeada a DTO
     * @throws ResourceNotFoundException si la categoría no existe
     */
    @Override
    public CategoriaResponse findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        return mapToResponse(categoria);
    }

    /**
     * Crea una nueva categoría.
     *
     * @param request los datos de la categoría a crear
     * @return la categoría creada mapeada a DTO
     */
    @Override
    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria categoria = new Categoria(null, request.nombre());
        Categoria guardada = categoriaRepository.save(categoria);
        return mapToResponse(guardada);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id      el identificador de la categoría a actualizar
     * @param request los nuevos datos de la categoría
     * @return la categoría actualizada mapeada a DTO
     * @throws ResourceNotFoundException si la categoría no existe
     */
    @Override
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        
        categoria.setNombre(request.nombre());
        Categoria guardada = categoriaRepository.save(categoria);
        return mapToResponse(guardada);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * <p>No se puede eliminar una categoría que posea productos asociados.</p>
     *
     * @param id el identificador de la categoría a eliminar
     * @throws ResourceNotFoundException si la categoría no existe
     * @throws BusinessRuleException     si la categoría posee productos asociados
     */
    @Override
    public void deleteById(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + id);
        }
        
        List<Producto> asociados = productoRepository.findByCategoria(id);
        if (!asociados.isEmpty()) {
            throw new BusinessRuleException("No se puede eliminar la categoría con ID " + id 
                    + " porque tiene " + asociados.size() + " producto(s) asociado(s).");
        }
        
        categoriaRepository.deleteById(id);
    }

    /**
     * Mapea una entidad {@link Categoria} a su DTO correspondiente {@link CategoriaResponse}.
     *
     * @param c la categoría a mapear
     * @return el DTO de respuesta
     */
    private CategoriaResponse mapToResponse(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNombre());
    }
}
