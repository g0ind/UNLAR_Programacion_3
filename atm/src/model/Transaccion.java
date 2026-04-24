package UNLAR_Programacion_3.atm.src.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    private TipoTransaccion tipo;
    private double monto;
    private LocalDateTime fechaHora;
    private String descripcion;
    private double saldoResultante;

    public Transaccion(TipoTransaccion tipo, double monto, String descripcion, double saldoResultante) {
        this.tipo = tipo;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
        this.descripcion = descripcion;
        this.saldoResultante = saldoResultante;
    }

    // Método para auditoría usando StringBuilder
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
          .append(String.format("%,.2f", saldoResultante));
          
        return sb.toString();
    }
}
