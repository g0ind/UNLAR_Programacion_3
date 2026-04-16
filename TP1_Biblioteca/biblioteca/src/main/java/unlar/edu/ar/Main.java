package unlar.edu.ar;

import unlar.edu.ar.exception.LibroNoDisponibleException;
import unlar.edu.ar.exception.LimitePrestamosExcedidoException;
import unlar.edu.ar.model.Estudiante;
import unlar.edu.ar.model.Libro;
import unlar.edu.ar.service.BibliotecaService;
import unlar.edu.ar.ui.ConsolaUI;

public class Main {
    public static void main(String[] args) {
        BibliotecaService service = new BibliotecaService();
        ConsolaUI ui = new ConsolaUI(service);

        ui.mostrarBienvenida();

        // 1. Carga de datos iniciales
        service.agregarLibro(new Libro("101", "Java para Sistemas", "Deitel", 2023));
        service.agregarLibro(new Libro("102", "Clean Code", "Robert Martin", 2008));
        service.agregarLibro(new Libro("103", "Patrones de Diseño", "GoF", 1994));
        service.agregarLibro(new Libro("104", "Algoritmos", "Sedgewick", 2011));
        service.agregarLibro(new Libro("105", "Sistemas Operativos", "Tanenbaum", 2009));
        
        service.agregarEstudiante(new Estudiante("111", "Marisa Chaile", "Sistemas", "mar@unlar.edu.ar"));
        service.agregarEstudiante(new Estudiante("222", "Pablo Galarza", "Programación", "pablo@unlar.edu.ar"));
        service.agregarEstudiante(new Estudiante("333", "Virginia Vera", "Sistemas", "test@unlar.edu.ar"));

        // --- 2. Prueba de Préstamo Exitoso ---
        System.out.println("\n[ TEST 1: Préstamo Exitoso ]");
        try {
            String legajo = "111";
            String isbn = "101";
            
            service.registrarPrestamo(legajo, isbn);
            String nombreE = service.buscarEstudiante(legajo).getNombre();
            String tituloL = service.buscarLibroPorIsbn(isbn).getTitulo();
            
            System.out.println(" >> OK: '" + tituloL + "' prestado a " + nombreE);
        } catch (Exception e) {
            System.out.println(" >> ERROR INESPERADO: " + e.getMessage());
        }
// --- 3. Prueba de Excepción: Libro No Disponible ---
        System.out.println("\n[ TEST 2: Libro No Disponible ]");
        try {
            // Intentamos que Pablo pida el libro 101 (que ya lo tiene Marisa)
            service.registrarPrestamo("222", "101");
        } catch (LibroNoDisponibleException e) {
            System.out.println(" >> CAPTURADA: " + e.getMessage());
        } catch (Exception e) { 
            System.out.println(" >> ERROR INESPERADO: " + e.getMessage()); 
        }

        // --- 4. Prueba de Excepción: Límite Excedido (Máximo 3 libros) ---
        System.out.println("\n[ TEST 3: Límite de 3 Libros ]");
        try {
            System.out.println(" Cargando libros a Pablo Galarza...");
            service.registrarPrestamo("222", "102"); 
            service.registrarPrestamo("222", "103"); 
            service.registrarPrestamo("222", "104"); 
            System.out.println(" >> OK: Pablo pidió 3 libros con éxito.");
            
            System.out.print(" Intentando pedir un 4to libro (ISBN 105)... ");
            // Pablo intenta pedir el 105 (que sí está en la biblioteca)
            service.registrarPrestamo("222", "105"); 
        } catch (LimitePrestamosExcedidoException e) {
            System.out.println("\n >> CAPTURADA: " + e.getMessage());
        } catch (Exception e) { 
            System.out.println("\n >> ERROR INESPERADO: " + e.getMessage()); 
        }

        // --- 5. Prueba de Recursividad (Multa) ---
        System.out.println("\n[ TEST 4: Cálculo de Multa Recursivo ]");
        double multa = service.calcularMulta(15, 5000.0);
        System.out.println(" >> OK: Multa por 15 días: $" + multa);
    }
}