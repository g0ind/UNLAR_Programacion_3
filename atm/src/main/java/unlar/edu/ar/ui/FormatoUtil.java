package unlar.edu.ar.ui;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatoUtil {
    
    /**
     * Da formato a la moneda según el requisito: $XXX,XXX.00
     */
    public static String formatearMoneda(double monto) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.US);
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        return formato.format(monto);
    }

    /**
     * Da formato a la fecha para el log de transacciones
     */
    public static String formatearFechaHora(LocalDateTime fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaHora.format(formatter);
    }
}