package unlar.edu.ar.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    // Marcamos los campos como final para garantizar la inmutabilidad
    private final TipoTransaccion tipo;
    private final double monto;
    private final LocalDateTime fechaHora;
    private final String descripcion;
    private final double saldoResultante;

    public Transaccion(TipoTransaccion tipo, double monto, String descripcion, double saldoResultante) {
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.saldoResultante = saldoResultante;
        this.fechaHora = LocalDateTime.now(); // Se captura el momento exacto
    }

    // Método para auditoría usando StringBuilder (Puntos extra por eficiencia)
    public String obtenerLog() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        
        sb.append("[")
          .append(fechaHora.format(formatter))
          .append("] ")
          .append(tipo.name())
          .append(": $")
          .append(String.format("%,.2f", monto))
          .append(" | Saldo: $")
          .append(String.format("%,.2f", saldoResultante))
          .append(" | ")
          .append(descripcion);
          
        return sb.toString();
    }

    // Getters necesarios para que otros objetos lean la info
    public TipoTransaccion getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getDescripcion() { return descripcion; }
    public double getSaldoResultante() { return saldoResultante; }
}