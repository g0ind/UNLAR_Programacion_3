package ar.edu.unlar.prog3.tp_comparable_comparator.demos;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;

public class Ejercicio5Demo {
    public static void main(String[] args) {
        List<Estudiante> lista = obtenerDatosPrueba();

        Comparator<Estudiante> porPromedioDesc = Comparator.comparingDouble(Estudiante::getPromedio).reversed();

        // 1. Promedio descendente, desempate por nombre alfabéticamente ascendente
        Comparator<Estudiante> promedioDescNombreAsc = porPromedioDesc.thenComparing(Estudiante::getNombre);

        // 2. Orden inverso con reversed() a partir del de promedio descendente
        Comparator<Estudiante> promedioAsc = porPromedioDesc.reversed();

        // 3. Cantidad de materias aprobadas descendente, desempate por nombre ascendente
        Comparator<Estudiante> materiasDescNombreAsc =
                Comparator.comparingInt(Estudiante::getCantidadMateriasAprobadas).reversed()
                        .thenComparing(Estudiante::getNombre);

        mostrar("Promedio desc + nombre asc (desempate)", lista, promedioDescNombreAsc);
        mostrar("Promedio asc (derivado con reversed())", lista, promedioAsc);
        mostrar("Materias desc + nombre asc (desempate)", lista, materiasDescNombreAsc);
    }

    private static void mostrar(String titulo, List<Estudiante> lista, Comparator<Estudiante> comparator) {
        List<Estudiante> copia = new ArrayList<>(lista);
        copia.sort(comparator);
        System.out.println("\n--- " + titulo + " ---");
        copia.forEach(System.out::println);
    }

    private static List<Estudiante> obtenerDatosPrueba() {
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
