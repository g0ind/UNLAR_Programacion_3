package com.inventory.smart.repository;

import com.inventory.smart.exception.ResourceNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación abstracta y genérica de un repositorio en memoria.
 *
 * <p>Utiliza un {@link ConcurrentHashMap} para almacenar los datos de forma
 * segura para hilos y un {@link AtomicLong} para generar identificadores numéricos únicos.</p>
 *
 * @param <T>  el tipo de la entidad gestionada
 * @param <ID> el tipo del identificador único de la entidad (normalmente Long)
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
public abstract class GenericInMemoryRepository<T, ID> implements IGenericRepository<T, ID> {

    /**
     * Constructor por defecto del repositorio en memoria.
     */
    protected GenericInMemoryRepository() {
    }

    /**
     * Mapa interno thread-safe para almacenar las entidades.
     */
    protected final ConcurrentHashMap<ID, T> dataStore = new ConcurrentHashMap<>();

    /**
     * Generador de identificadores autoincrementales y thread-safe.
     */
    protected final AtomicLong idGenerator = new AtomicLong(0);

    /**
     * Recupera todas las entidades almacenadas en el repositorio.
     *
     * @return una lista con todos los elementos almacenados
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    /**
     * Busca una entidad por su identificador único.
     *
     * @param id identificador de la entidad
     * @return un Optional con la entidad si existe, o vacío en caso contrario
     */
    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(dataStore.get(id));
    }

    /**
     * Guarda la entidad provista.
     *
     * <p>Si el identificador de la entidad es nulo o 0, genera uno nuevo utilizando
     * el {@code idGenerator} e inyecta el valor a la entidad. Si la entidad ya posee ID,
     * actualiza la entrada existente.</p>
     *
     * @param entity la entidad a guardar
     * @return la entidad guardada con su respectivo identificador asignado
     */
    @SuppressWarnings("unchecked")
    @Override
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        
        Long id = getEntityId(entity);
        if (id == null || id == 0L) {
            id = idGenerator.incrementAndGet();
            setEntityId(entity, id);
        }
        
        dataStore.put((ID) id, entity);
        return entity;
    }

    /**
     * Elimina una entidad por su identificador único.
     *
     * @param id el identificador de la entidad a eliminar
     * @throws ResourceNotFoundException si la entidad con el ID especificado no existe
     */
    @Override
    public void deleteById(ID id) {
        if (id == null || !dataStore.containsKey(id)) {
            throw new ResourceNotFoundException("Entidad con ID " + id + " no encontrada.");
        }
        dataStore.remove(id);
    }

    /**
     * Comprueba la existencia de una entidad en base a su identificador único.
     *
     * @param id el identificador a buscar
     * @return true si la entidad existe en el almacén de datos, false en caso contrario
     */
    @Override
    public boolean existsById(ID id) {
        if (id == null) {
            return false;
        }
        return dataStore.containsKey(id);
    }

    /**
     * Retorna la cantidad total de elementos registrados.
     *
     * @return el tamaño total del almacén de datos
     */
    @Override
    public long count() {
        return dataStore.size();
    }

    /**
     * Intenta extraer el ID de la entidad a través de reflexión (método getId o campo id).
     *
     * @param entity la entidad de la cual extraer el ID
     * @return el ID si existe, o null en caso contrario
     */
    private Long getEntityId(T entity) {
        try {
            Method method = entity.getClass().getMethod("getId");
            return (Long) method.invoke(entity);
        } catch (Exception e) {
            try {
                Field field = entity.getClass().getDeclaredField("id");
                field.setAccessible(true);
                return (Long) field.get(entity);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Intenta asignar el ID a la entidad a través de reflexión (método setId o campo id).
     *
     * @param entity la entidad a la cual asignar el ID
     * @param id el identificador a asignar
     */
    private void setEntityId(T entity, Long id) {
        try {
            Method method = entity.getClass().getMethod("setId", Long.class);
            method.invoke(entity, id);
        } catch (Exception e) {
            try {
                Field field = entity.getClass().getDeclaredField("id");
                field.setAccessible(true);
                field.set(entity, id);
            } catch (Exception ex) {
                // Ignorar error si no se encuentra
            }
        }
    }
}
