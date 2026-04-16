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

// 1. Carga de datos iniciales (Mínimo 5 libros y 3 estudiantes según consigna)
        service.agregarLibro(new Libro("101", "Java para Sistemas", "Deitel", 2023));
        service.agregarLibro(new Libro("102", "Clean Code", "Robert Martin", 2008));
        service.agregarLibro(new Libro("103", "Patrones de Diseño", "GoF", 1994));
        service.agregarLibro(new Libro("104", "Algoritmos", "Sedgewick", 2011));
        service.agregarLibro(new Libro("105", "Sistemas Operativos", "Tanenbaum", 2009));
        
        service.agregarEstudiante(new Estudiante("111", "Marisa Chaile", "Sistemas", "mar@unlar.edu.ar"));
        service.agregarEstudiante(new Estudiante("222", "Pablo", "Programación", "pablo@unlar.edu.ar"));
        service.agregarEstudiante(new Estudiante("333", "Virginia", "Sistemas", "test@unlar.edu.ar"));


        // 2. Prueba de Préstamo Exitoso
       // 2. Prueba de Préstamo Exitoso (DINÁMICO)
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

        
}