package ar.edu.unlar.prog3.tp_comparable_comparator.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;

@Repository
public class EstudianteRepository {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    public List<Estudiante> findAll() {
        return new ArrayList<>(estudiantes);
    }

    public void agregar(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }
}
