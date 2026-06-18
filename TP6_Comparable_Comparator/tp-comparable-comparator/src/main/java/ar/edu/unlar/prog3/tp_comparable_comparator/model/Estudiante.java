package ar.edu.unlar.prog3.tp_comparable_comparator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Ejercicio 2: Estudiante implementa Comparable<Estudiante>.
 * El orden natural es por promedio DESCENDENTE (mayor mérito académico primero).
 *
 * Usamos Double.compare() en vez de resta para no romper el contrato
 * (ver Ejercicio 6 para el detalle de por qué la resta es peligrosa).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Estudiante implements Comparable<Estudiante> {

    private String legajo;
    private String nombre;
    private double promedio;
    private int edad;
    private int cantidadMateriasAprobadas;

    @Override
    public int compareTo(Estudiante otro) {
        // Invertimos el orden de los argumentos para que sea DESCENDENTE
        return Double.compare(otro.promedio, this.promedio);
    }
}
