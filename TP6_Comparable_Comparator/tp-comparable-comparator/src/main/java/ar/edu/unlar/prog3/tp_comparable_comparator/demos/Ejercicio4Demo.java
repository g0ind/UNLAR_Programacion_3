package ar.edu.unlar.prog3.tp_comparable_comparator.demos;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;

/**
 * Ejercicio 4: Comparators con lambda explícita y con Comparator.comparing() + method reference.
 */
public class Ejercicio4Demo {
    public static void main(String[] args) {
        List<Estudiante> lista = datos();

        // 1. Lambda explícita: materiasAprobadas ascendente, con Integer.compare (sin resta)
        Comparator<Estudiante> porMateriasAsc =
                (e1, e2) -> Integer.compare(e1.getCantidadMateriasAprobadas(), e2.getCantidadMateriasAprobadas());

        // 2. Comparator.comparing() + method reference: nombre alfabético
        Comparator<Estudiante> porNombre = Comparator.comparing(Estudiante::getNombre);

        // 3. Comparator.comparing() + method reference: edad ascendente
        Comparator<Estudiante> porEdad = Comparator.comparing(Estudiante::getEdad);

        mostrar("Por materias aprobadas (asc, lambda)", lista, porMateriasAsc);
        mostrar("Por nombre (alfabético, method reference)", lista, porNombre);
        mostrar("Por edad (asc, method reference)", lista, porEdad);
    }

    private static void mostrar(String titulo, List<Estudiante> lista, Comparator<Estudiante> comparator) {
        List<Estudiante> copia = new ArrayList<>(lista);
        copia.sort(comparator);
        System.out.println("\n--- " + titulo + " ---");
        copia.forEach(System.out::println);
    }

    private static List<Estudiante> datos() {
        List<Estudiante> lista = new ArrayList<>();
        lista.add(new Estudiante("LU-2024-001", "Martín Quiroga", 8.5, 22, 18));
        lista.add(new Estudiante("LU-2024-002", "Valeria Díaz", 8.5, 20, 15));
        lista.add(new Estudiante("LU-2024-003", "Facundo Castro", 7.2, 24, 22));
        lista.add(new Estudiante("LU-2024-004", "Camila Torres", 9.1, 21, 24));
        lista.add(new Estudiante("LU-2024-005", "Lucas González", 9.1, 23, 24));
        lista.add(new Estudiante("LU-2024-006", "Agustina López", 6.8, 19, 10));
        lista.add(new Estudiante("LU-2024-007", "Nahuel Herrera", 7.5, 22, 14));
        lista.add(new Estudiante("LU-2024-008", "Florencia Ríos", 8.9, 25, 20));
        lista.add(new Estudiante("LU-2024-009", "Tomás Sosa", 6.5, 20, 12));
        lista.add(new Estudiante("LU-2024-010", "Lucía Fernández", 7.8, 21, 16));
        return lista;
    }
}

