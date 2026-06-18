package ar.edu.unlar.prog3.tp_comparable_comparator.demos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.model.Estudiante;


/* chequea, lo entendi asi */
public class Ejercicio6Demo {

    public static void main(String[] args) {
        Estudiante e1 = new Estudiante("LU-2024-999", "Caso Edad Maxima", 7.0, Integer.MAX_VALUE, 10);
        Estudiante e2 = new Estudiante("LU-2024-998", "Caso Edad Negativa", 7.0, -1, 10);

        List<Estudiante> conResta = new ArrayList<>(List.of(e2, e1));
        List<Estudiante> conCompare = new ArrayList<>(List.of(e2, e1));

        // Truco de la resta (INCORRECTO)
        Comparator<Estudiante> restaTramposa = (a, b) -> a.getEdad() - b.getEdad();

        Comparator<Estudiante> conIntegerCompare = (a, b) -> Integer.compare(a.getEdad(), b.getEdad());

        conResta.sort(restaTramposa);
        conCompare.sort(conIntegerCompare);

        System.out.println("Edades a comparar: " + e1.getEdad() + " y " + e2.getEdad());

        System.out.println("\n--- Ordenado con resta (resultado del sort) ---");
        conResta.forEach(e -> System.out.println(e.getNombre() + " -> Edad: " + e.getEdad()));
        // Resultado esperado: Maxima queda primero, Negativa segundo -> ORDEN INCORRECTO

        System.out.println("\n--- Ordenado con Integer.compare (resultado del sort) ---");
        conCompare.forEach(e -> System.out.println(e.getNombre() + " -> Edad: " + e.getEdad()));
        // Resultado esperado: Negativa primero, Maxima segundo -> ORDEN CORRECTO

        System.out.println("\n--- Inspeccionando el comparator directamente (prueba del bug) ---");
        System.out.println("restaTramposa.compare(e1, e2) = " + restaTramposa.compare(e1, e2)
                + " (deberia ser POSITIVO, e1 tiene mas edad)");
        System.out.println("restaTramposa.compare(e2, e1) = " + restaTramposa.compare(e2, e1)
                + " (mismo valor que el anterior: viola la antisimetria del contrato)");

        System.out.println("conIntegerCompare.compare(e1, e2) = " + conIntegerCompare.compare(e1, e2)
                + " (correctamente POSITIVO)");
        System.out.println("conIntegerCompare.compare(e2, e1) = " + conIntegerCompare.compare(e2, e1)
                + " (correctamente NEGATIVO)");
    }
}
