package ar.edu.unlar.prog3.tp_comparable_comparator.config;

import org.springframework.stereotype.Component;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;
import ar.edu.unlar.prog3.tp_comparable_comparator.repository.EstudianteRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final EstudianteRepository repository;

    @PostConstruct
    public void cargarDatos() {
        repository.agregar(new Estudiante("LU-2024-001", "Martín Quiroga", 8.5, 22, 18));
        repository.agregar(new Estudiante("LU-2024-002", "Valeria Díaz", 8.5, 20, 15));
        repository.agregar(new Estudiante("LU-2024-003", "Facundo Castro", 7.2, 24, 22));
        repository.agregar(new Estudiante("LU-2024-004", "Camila Torres", 9.1, 21, 24));
        repository.agregar(new Estudiante("LU-2024-005", "Lucas González", 9.1, 23, 24));
        repository.agregar(new Estudiante("LU-2024-006", "Agustina López", 6.8, 19, 10));
        repository.agregar(new Estudiante("LU-2024-007", "Nahuel Herrera", 7.5, 22, 14));
        repository.agregar(new Estudiante("LU-2024-008", "Florencia Ríos", 8.9, 25, 20));
        repository.agregar(new Estudiante("LU-2024-009", "Tomás Sosa", 6.5, 20, 12));
        repository.agregar(new Estudiante("LU-2024-010", "Lucía Fernández", 7.8, 21, 16));
    }
}
