package unlar.edu.ar.model;

import java.time.LocalDate;
import java.util.Objects;

public class Prestamo {
    private Libro libro;
    private Estudiante estudiante;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(Libro libro, Estudiante estudiante) {
        this.libro = libro;
        this.estudiante = estudiante;
        this.fechaPrestamo = LocalDate.now(); // Fecha de hoy
    }

    public Prestamo() {}

    // Getters y Setters
    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    @Override
    public String toString() {
        return "Préstamo: " + libro.getTitulo() + " -> " + estudiante.getNombre();
    }

    // Equals y HashCode (Vital para el HashSet del Service)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestamo prestamo = (Prestamo) o;
        return Objects.equals(libro, prestamo.libro) && 
               Objects.equals(estudiante, prestamo.estudiante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(libro, estudiante);
    }
}