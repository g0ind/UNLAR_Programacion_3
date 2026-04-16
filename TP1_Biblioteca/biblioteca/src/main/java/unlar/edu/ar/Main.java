package unlar.edu.ar;

import unlar.edu.ar.exception.LibroNoDisponibleException;
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
        service.agregarLibro(new Libro("111", "Java para Sistemas", "Deitel", 2023));
        service.agregarLibro(new Libro("222", "Clean Code", "Robert Martin", 2008));
        service.agregarLibro(new Libro("333", "Patrones de Diseño", "GoF", 1994));
        
        service.agregarEstudiante(new Estudiante("101", "Marisa Chaile", "Sistemas", "mar@unlar.edu.ar"));
        service.agregarEstudiante(new Estudiante("102", "Lea", "Programacion", "lea@unlar.edu.ar"));

        // 2. Prueba de Préstamo Exitoso
        try {
            service.registrarPrestamo("111", "101");
            System.out.println(" Préstamo registrado: Java para Sistemas a Marisa");
        } catch (Exception e) {
            System.out.println(" Error inesperado: " + e.getMessage());
        }

        // 3. Prueba de Excepción: Libro No Disponible
        try {
            System.out.println("\nIntentando pedir un libro ya prestado...");
            service.registrarPrestamo("111", "102");
        } catch (LibroNoDisponibleException e) {
            System.out.println(" Capturada: " + e.getMessage());
        } catch (Exception e) { e.printStackTrace(); }

        // 4. Prueba de Recursividad (Multa)
        System.out.println("\n--- Cálculo de Multa (Recursivo) ---");
        double multa = service.calcularMulta(15, 5000.0); // 15 días de retraso
        System.out.println("Multa por 15 días: $" + multa);
    }
}