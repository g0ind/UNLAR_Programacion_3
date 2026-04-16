package unlar.edu.ar.model;

public class Estudiante {
    // 2.1 Atributos privados (Encapsulamiento)
    private String legajo;
    private String nombre;
    private String carrera;
    private String email;

    // 3.1 Constructor parametrizado (para crear el estudiante con datos)
    public Estudiante(String legajo, String nombre, String carrera, String email) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.carrera = carrera;
        this.email = email;
    }

    // 3.1 Constructor por defecto (vacio, por si se necesita)
    public Estudiante() {
    }

    // 3.0 Getters y Setters (Las "puertas" de acceso)
    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 3.2 Método toString() para mostrar los datos lindos en la consola
    @Override
    public String toString() {
        return "Estudiante: [Legajo=" + legajo + ", Nombre=" + nombre + 
               ", Carrera=" + carrera + ", Email=" + email + "]";
    }
}
