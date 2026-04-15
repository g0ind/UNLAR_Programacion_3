package unlar.edu.ar.model;

import java.util.Objects;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anio;
    private boolean disponible;

    // Constructor con datos
    public Libro(String isbn, String titulo, String autor, int anio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.disponible = true; // Todo libro nuevo arranca disponible
    }

    // Constructor vacío (Requisito 3.1)
    public Libro() {}

    // Getters y Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "Libro: " + titulo + " [" + isbn + "] - Estado: " + (disponible ? "Disponible" : "Prestado");
    }

    // Equals y HashCode por ISBN (para que no haya libros repetidos por error)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}