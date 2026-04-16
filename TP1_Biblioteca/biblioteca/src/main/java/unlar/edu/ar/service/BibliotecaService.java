package unlar.edu.ar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import unlar.edu.ar.exception.EstudianteNoEncontradoException;
import unlar.edu.ar.exception.LibroNoDisponibleException;
import unlar.edu.ar.exception.LimitePrestamosExcedidoException;
import unlar.edu.ar.model.Estudiante;
import unlar.edu.ar.model.Libro;
import unlar.edu.ar.model.Prestamo;

public class BibliotecaService {
    // 2.2 Estructuras de Datos requeridas
    private ArrayList<Libro> catalogo = new ArrayList<>();
    private HashMap<String, Estudiante> registroEstudiantes = new HashMap<>();
    private HashSet<Prestamo> prestamosActivos = new HashSet<>();

    // 2.5 Lógica de Multa Recursiva
    // 1% por día de retraso, máximo 30 días calculables
    public double calcularMulta(int diasRetraso, double valorLibro) {
        // Caso Base 1: No hay retraso o terminamos la cuenta
        if (diasRetraso <= 0) {
            return 0;
        }
        // Caso Base 2: Límite de 30 días según consigna
        if (diasRetraso > 30) {
            return calcularMulta(30, valorLibro); 
        }

        // Llamada recursiva: 1% del valor + multa del día anterior
        double montoDiaActual = valorLibro * 0.01;
        return montoDiaActual + calcularMulta(diasRetraso - 1, valorLibro);
    }

    public void registrarPrestamo(String isbn, String legajo) 
            throws LibroNoDisponibleException, EstudianteNoEncontradoException, LimitePrestamosExcedidoException {
        
        // 1. Validar Estudiante
        Estudiante est = registroEstudiantes.get(legajo);
        if (est == null) throw new EstudianteNoEncontradoException("Legajo " + legajo + " no existe.");

        // 2. Validar Libro
        Libro lib = buscarLibroPorIsbn(isbn);
        if (lib == null || !lib.isDisponible()) {
            throw new LibroNoDisponibleException("El libro con ISBN " + isbn + " no está disponible.");
        }

        // 3. Validar Límite (Máximo 3)
        long cantidad = prestamosActivos.stream()
                        .filter(p -> p.getEstudiante().getLegajo().equals(legajo))
                        .count();
        if (cantidad >= 3) throw new LimitePrestamosExcedidoException("El estudiante ya tiene 3 libros.");

        // Si todo está ok, registramos
        Prestamo nuevo = new Prestamo(lib, est);
        lib.setDisponible(false);
        prestamosActivos.add(nuevo);
    }

    // Método auxiliar para buscar en el ArrayList
    private Libro buscarLibroPorIsbn(String isbn) {
        for (Libro l : catalogo) {
            if (l.getIsbn().equals(isbn)) return l;
        }
        return null;
    }

    // 2.4 Registrar devolución y liberar libro
    public double registrarDevolucion(String isbn, String legajo, int diasRetraso) {
        Prestamo prestamoEncontrado = null;
        
        // Buscamos el préstamo en el HashSet
        for (Prestamo p : prestamosActivos) {
            if (p.getLibro().getIsbn().equals(isbn) && p.getEstudiante().getLegajo().equals(legajo)) {
                prestamoEncontrado = p;
                break;
            }
        }

        if (prestamoEncontrado != null) {
            prestamosActivos.remove(prestamoEncontrado);
            prestamoEncontrado.getLibro().setDisponible(true);
            
            // Si hay retraso, calculamos la multa con el método recursivo
            // Supongamos un valor fijo de libro para el ejemplo o sacalo del modelo si lo agregás
            return calcularMulta(diasRetraso, 1000.0); 
        }
        return 0;
    }

    // 2.4 Búsqueda parcial por título (case-insensitive)
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro l : catalogo) {
            if (l.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(l);
            }
        }
        return resultados;
    }

    // Métodos para cargar datos iniciales
    public void agregarLibro(Libro libro) {
        catalogo.add(libro);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        registroEstudiantes.put(estudiante.getLegajo(), estudiante);
    }
    // 2.4 Listar préstamos por estudiante específico
    public void listarPrestamosPorEstudiante(String legajo) {
        System.out.println("Préstamos del estudiante con legajo: " + legajo);
        prestamosActivos.stream()
            .filter(p -> p.getEstudiante().getLegajo().equals(legajo))
            .forEach(System.out::println);
    }

}
