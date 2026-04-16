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
    private final ArrayList<Libro> catalogo = new ArrayList<>();
    private final HashMap<String, Estudiante> registroEstudiantes = new HashMap<>();
    private final HashSet<Prestamo> prestamosActivos = new HashSet<>();

    // --- MÉTODOS DE BÚSQUEDA ---
    
    public Estudiante buscarEstudiante(String legajo) {
        return registroEstudiantes.get(legajo);
    }

    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    // --- 2.5 LÓGICA DE MULTA RECURSIVA ---
    public double calcularMulta(int diasRetraso, double valorLibro) {
        if (diasRetraso <= 0) return 0;
        if (diasRetraso > 30) return calcularMulta(30, valorLibro); 

        double montoDiaActual = valorLibro * 0.01;
        return montoDiaActual + calcularMulta(diasRetraso - 1, valorLibro);
    }

    // --- 2.4 REGISTRAR PRÉSTAMO ---
    // Cambié el orden a (legajo, isbn) para que coincida con el Main dinámico
    public void registrarPrestamo(String legajo, String isbn) 
            throws LibroNoDisponibleException, EstudianteNoEncontradoException, LimitePrestamosExcedidoException {
        
        Estudiante est = buscarEstudiante(legajo);
        if (est == null) throw new EstudianteNoEncontradoException("Legajo " + legajo + " no existe.");

        Libro lib = buscarLibroPorIsbn(isbn);
        if (lib == null || !lib.isDisponible()) {
            throw new LibroNoDisponibleException("El libro con ISBN " + isbn + " no está disponible.");
        }

        // Validar Límite (Máximo 3) antes de registrar el préstamo
        long cantidad = prestamosActivos.stream()
                        .filter(p -> p.getEstudiante().getLegajo().equals(legajo))
                        .count();
        if (cantidad >= 3) throw new LimitePrestamosExcedidoException("El estudiante ya tiene 3 libros.");

        // Registro exitoso
        Prestamo nuevo = new Prestamo(lib, est);
        lib.setDisponible(false);
        prestamosActivos.add(nuevo);
    }

    // ---  REGISTRAR DEVOLUCIÓN ---
    public double registrarDevolucion(String legajo, String isbn, int diasRetraso) {
        Prestamo prestamoEncontrado = null;
        for (Prestamo p : prestamosActivos) {
            if (p.getLibro().getIsbn().equals(isbn) && p.getEstudiante().getLegajo().equals(legajo)) {
                prestamoEncontrado = p;
                break;
            }
        }

        if (prestamoEncontrado != null) {
            prestamosActivos.remove(prestamoEncontrado);
            prestamoEncontrado.getLibro().setDisponible(true);
            return calcularMulta(diasRetraso, 1000.0); 
        }
        return 0;
    }

    // --- BÚSQUEDAS Y LISTADOS ---

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro l : catalogo) {
            if (l.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultados.add(l);
            }
        }
        return resultados;
    }

    public void agregarLibro(Libro libro) {
        catalogo.add(libro);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        registroEstudiantes.put(estudiante.getLegajo(), estudiante);
    }

    public void listarPrestamosPorEstudiante(String legajo) {
        System.out.println("Préstamos del estudiante con legajo: " + legajo);
        prestamosActivos.stream()
            .filter(p -> p.getEstudiante().getLegajo().equals(legajo))
            .forEach(System.out::println);
    }
}