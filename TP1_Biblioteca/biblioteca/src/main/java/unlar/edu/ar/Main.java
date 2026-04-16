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

// 1. Carga de datos iniciales (Mínimo 5 libros y 3 estudiantes según consigna)
        service.agregarLibro(new Libro("101", "Java para Sistemas", "Deitel", 2023));
        service.agregarLibro(new Libro("102", "Clean Code", "Robert Martin", 2008));
        service.agregarLibro(new Libro("103", "Patrones de Diseño", "GoF", 1994));
        service.agregarLibro(new Libro("104", "Algoritmos", "Sedgewick", 2011));
        service.agregarLibro(new Libro("105", "Sistemas Operativos", "Tanenbaum", 2009));
        
        service.agregarEstudiante(new Estudiante("111", "Marisa Chaile", "Sistemas", "mar@unlar.edu.ar"));
        service.agregarEstudiante(new Estudiante("222", "Pablo Galarza", "Programación", "pablo@unlar.edu.ar"));
        service.agregarEstudiante(new Estudiante("333", "Virginia Vera", "Sistemas", "test@unlar.edu.ar"));


        // 2. Prueba de Préstamo Exitoso

        try {
            String legajo = "111";
            String isbn = "101";
            
            service.registrarPrestamo(legajo, isbn);
            
            // Buscamos los datos para que el mensaje no sea "fijo"
            String nombreE = service.buscarEstudiante(legajo).getNombre();
            String tituloL = service.buscarLibroPorIsbn(isbn).getTitulo();
            
            System.out.println(" Préstamo registrado: '" + tituloL + "' a " + nombreE);
        } catch (Exception e) {
            System.out.println(" Error inesperado: " + e.getMessage());
        }
// 3. Prueba de Excepción: Libro No Disponible
        try {
            System.out.println("\n--- Test: Libro No Disponible ---");
            // Intentamos pedir el mismo libro "101" con otro estudiante
            service.registrarPrestamo("222", "101");
        } catch (LibroNoDisponibleException e) {
            System.out.println(" Capturada correctamente: " + e.getMessage());
        } catch (Exception e) { e.printStackTrace(); }

        // 4. Prueba de Excepción: Límite Excedido (Máximo 3 libros)
        try {
            System.out.println("\n--- Test: Límite de Préstamos (Estudiante Pablo) ---");
            service.registrarPrestamo("222", "102"); // Libro 1
            service.registrarPrestamo("222", "103"); // Libro 2
            service.registrarPrestamo("222", "104"); // Libro 3
            System.out.println(" Pablo pidió 3 libros con éxito.");
            service.registrarPrestamo("111", "105"); 
            System.err.println(" Marisa pidió un 4to libro, pero debería haber lanzado una excepción.");
            
            System.out.println(" Intentando pedir un 4to libro para Marisa...");
            service.registrarPrestamo("222", "105"); 
        } catch (LimitePrestamosExcedidoException e) {
            System.out.println(" Capturada correctamente: " + e.getMessage());
        } catch (Exception e) { e.printStackTrace(); }

        // 5. Prueba de Recursividad (Multa)
        System.out.println("\n--- Cálculo de Multa (Recursivo) ---");
        double multa = service.calcularMulta(15, 5000.0); // 15 días de retraso
        System.out.println("Multa por 15 días: $" + multa);
    }
        
}