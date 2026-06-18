package ar.edu.unlar.prog3.tp_comparable_comparator.service;

import ar.edu.unlar.prog3.tp_comparable_comparator.exception.CriterioOrdenInvalidoException;
import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstudianteService {

    private final Map<String, Comparator<Estudiante>> estrategias = new LinkedHashMap<>();

    public EstudianteService() {
        estrategias.put("promedio",
                Comparator.comparingDouble(Estudiante::getPromedio)
                        .thenComparing(Estudiante::getLegajo));

        estrategias.put("edad",
                Comparator.comparingInt(Estudiante::getEdad)
                        .thenComparing(Estudiante::getLegajo));

        estrategias.put("nombre",
                Comparator.comparing(Estudiante::getNombre)
                        .thenComparing(Estudiante::getLegajo));

        estrategias.put("materiasAprobadas",
                Comparator.comparingInt(Estudiante::getCantidadMateriasAprobadas)
                        .thenComparing(Estudiante::getLegajo));

        estrategias.put("legajo",
                Comparator.comparing(Estudiante::getLegajo));
    }

    public List<Estudiante> ordenar(List<Estudiante> lista, String sortBy, String order) {
        Comparator<Estudiante> comparator = estrategias.get(sortBy);

        if (comparator == null) {
            throw new CriterioOrdenInvalidoException(sortBy, estrategias.keySet());
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        lista.sort(comparator);
        return lista;
    }
}
