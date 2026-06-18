package ar.edu.unlar.prog3.tp_comparable_comparator.demos;


import java.util.ArrayList;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;

public class Ejercicio1Demo {
    public static void main(String[] args) {
        List<Estudiante> lista = new ArrayList<>();
        lista.add(new Estudiante("LU-2024-001", "Martín Quiroga", 8.5, 22, 18));
        lista.add(new Estudiante("LU-2024-002", "Valeria Díaz", 8.5, 20, 15));
        lista.add(new Estudiante("LU-2024-003", "Facundo Castro", 7.2, 24, 22));
        lista.add(new Estudiante("LU-2024-004", "Camila Torres", 9.1, 21, 24));
        lista.add(new Estudiante("LU-2024-005", "Lucas González", 9.1, 23, 24));

        // Collections.sort(lista);
        /*
        Error de compilación obtenido:
        java: no suitable method found for sort(java.util.List<ar.edu.unlar.prog3.tpcomparablecomparator.model.Estudiante>)
            method java.util.Collections.<T>sort(java.util.List<T>) is not applicable
              (inferred type does not conform to upper bound(s)
                inferred: ar.edu.unlar.prog3.tpcomparablecomparator.model.Estudiante
                upper bound(s): java.lang.Comparable<? super ar.edu.unlar.prog3.tpcomparablecomparator.model.Estudiante>,java.lang.Object)
        */

        System.out.println("Lista de estudiantes:");
        lista.forEach(System.out::println);
    }
}

