package ar.edu.unlar.prog3.tp_comparable_comparator.demos;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;

public class Ejercicio2Demo {
    public static void main(String[] args) {
        List<Estudiante> lista = new ArrayList<>();
        lista.add(new Estudiante("LU-2024-001", "Martín Quiroga", 8.5, 22, 18));
        lista.add(new Estudiante("LU-2024-002", "Valeria Díaz", 8.5, 20, 15));
        lista.add(new Estudiante("LU-2024-003", "Facundo Castro", 7.2, 24, 22));
        lista.add(new Estudiante("LU-2024-004", "Camila Torres", 9.1, 21, 24));
        lista.add(new Estudiante("LU-2024-005", "Lucas González", 9.1, 23, 24));

        System.out.println("--- Lista ANTES de ordenar (Orden Natural) ---");
        lista.forEach(System.out::println);

        Collections.sort(lista);

        System.out.println("\n--- Lista DESPUÉS de ordenar (Orden Natural - Promedio Descendente) ---");
        lista.forEach(System.out::println);
    }
}
