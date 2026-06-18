package ar.edu.unlar.prog3.tp_comparable_comparator.demos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;

public class Ejercicio6Demo {
    public static void main(String[] args) {
        // Creamos los dos estudiantes con edades extremas (e1: Negativa, e2: Máxima)
        Estudiante e1 = new Estudiante("LU-2024-998", "Estudiante A (Edad Negativa)", 7.0, -1, 10);
        Estudiante e2 = new Estudiante("LU-2024-999", "Estudiante B (Edad Máxima)", 7.0, Integer.MAX_VALUE, 10);

        System.out.println("======================================================================");
        System.out.println("DEMOSTRACIÓN DE OVERFLOW EN COMPARADORES (Ejercicio 6)");
        System.out.println("======================================================================");
        System.out.println("Edades a comparar:");
        System.out.println(" - " + e1.getNombre() + ": " + e1.getEdad());
        System.out.println(" - " + e2.getNombre() + ": " + e2.getEdad());
        System.out.println("----------------------------------------------------------------------");

        // 1. Explicación matemática del desbordamiento en consola
        System.out.println("Cálculo de la comparación interna (e2 vs e1):");
        
        // Comparación con resta: e2 - e1 -> MAX_VALUE - (-1)
        int resultadoResta = e2.getEdad() - e1.getEdad();
        System.out.println("  * Usando RESTA: " + e2.getEdad() + " - (" + e1.getEdad() + ") = " + resultadoResta);
        System.out.println("    -> Como el resultado es NEGATIVO (" + resultadoResta + "), el comparador cree incorrectamente que B < A.");

        // Comparación con Integer.compare
        int resultadoCompare = Integer.compare(e2.getEdad(), e1.getEdad());
        System.out.println("  * Usando Integer.compare: " + resultadoCompare);
        System.out.println("    -> Como el resultado es POSITIVO (" + resultadoCompare + "), el comparador sabe correctamente que B > A.");
        System.out.println("----------------------------------------------------------------------");

        // 2. Ejecución del ordenamiento
        // Iniciamos la lista: [e1, e2] (Edad Negativa primero, Edad Máxima después)
        List<Estudiante> conResta = new ArrayList<>(List.of(e1, e2));
        List<Estudiante> conCompare = new ArrayList<>(List.of(e1, e2));

        Comparator<Estudiante> restaTramposa = (a, b) -> a.getEdad() - b.getEdad();
        Comparator<Estudiante> conIntegerCompare = (a, b) -> Integer.compare(a.getEdad(), b.getEdad());

        conResta.sort(restaTramposa);
        conCompare.sort(conIntegerCompare);

        // 3. Resultados y validación de ordenamiento
        System.out.println("RESULTADO DE ORDENAMIENTO (Debería quedar A y luego B):");
        
        System.out.println("\n--- Ordenado con resta (INCORRECTO - Overflow) ---");
        conResta.forEach(e -> System.out.println(" * " + e.getNombre() + " -> Edad: " + e.getEdad()));
        boolean restaCorrecta = conResta.get(0).getEdad() < conResta.get(1).getEdad();
        System.out.println("Estado de la lista: " + (restaCorrecta ? "[✔ ORDEN CORRECTO]" : "[✘ ORDEN INCORRECTO - ¡Falló el ordenamiento por el overflow!]"));

        System.out.println("\n--- Ordenado con Integer.compare (CORRECTO) ---");
        conCompare.forEach(e -> System.out.println(" * " + e.getNombre() + " -> Edad: " + e.getEdad()));
        boolean compareCorrecto = conCompare.get(0).getEdad() < conCompare.get(1).getEdad();
        System.out.println("Estado de la lista: " + (compareCorrecto ? "[✔ ORDEN CORRECTO]" : "[✘ ORDEN INCORRECTO]"));
        System.out.println("======================================================================");
    }
}
