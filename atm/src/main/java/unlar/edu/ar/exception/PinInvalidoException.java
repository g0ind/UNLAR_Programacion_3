package unlar.edu.ar.exception;

public class PinInvalidoException extends Exception {
    private int intentosRestantes;

    public PinInvalidoException(int intentosRestantes) {
        super("PIN incorrecto. Intentos restantes: " + intentosRestantes);
        this.intentosRestantes = intentosRestantes;
    }

    public int getIntentosRestantes() { return intentosRestantes; }
}
