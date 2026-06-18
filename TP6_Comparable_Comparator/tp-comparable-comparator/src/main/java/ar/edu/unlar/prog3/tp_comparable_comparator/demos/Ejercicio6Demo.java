package ar.edu.unlar.prog3.tp_comparable_comparator.demos;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;

public class Ejercicio6Demo {
    public static void main(String[] args) {
        Estudiante e1 = new Estudiante("LU-2024-999", "Caso Edad MÃ¡xima", 7.0, Integer.MAX_VALUE, 10);
        Estudiante e2 = new Estudiante("LU-2024-998", "Caso Edad Negativa", 7.0, -1, 10);

        List<Estudiante> conResta = new ArrayList<>(List.of(e1, e2));
        List<Estudiante> conCompare = new ArrayList<>(List.of(e1, e2));

        Comparator<Estudiante> restaTramposa = (a, b) -> a.getEdad() - b.getEdad();
        Comparator<Estudiante> conIntegerCompare = (a, b) -> Integer.compare(a.getEdad(), b.getEdad());

        conResta.sort(restaTramposa);
        conCompare.sort(conIntegerCompare);

        System.out.println("Edades a comparar: " + e1.getEdad() + " y " + e2.getEdad());

        System.out.println("\n--- Ordenado con resta (INCORRECTO - Overflow) ---");
        conResta.forEach(e -> System.out.println(e.getNombre() + " -> Edad: " + e.getEdad()));

        System.out.println("\n--- Ordenado con Integer.compare (CORRECTO) ---");
        conCompare.forEach(e -> System.out.println(e.getNombre() + " -> Edad: " + e.getEdad()));
    }
}
