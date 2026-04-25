package unlar.edu.ar.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatoUtil {
    
    /**
     * Da formato a la moneda según el requisito
     */
    public static String formatearMoneda(double monto) {
        // Usamos Locale.US para asegurar que el separador de miles sea ',' y el decimal '.'
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("$#,##0.00", simbolos);
        return df.format(monto);
    }


    public static String formatearFechaHora(LocalDateTime fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaHora.format(formatter);
    }
}