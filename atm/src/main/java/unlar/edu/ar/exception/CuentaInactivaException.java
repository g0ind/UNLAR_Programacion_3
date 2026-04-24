package unlar.edu.ar.exception;

public class CuentaInactivaException extends Exception {
    public CuentaInactivaException(String numeroCuenta) {
        super("La cuenta " + numeroCuenta + " está inactiva. Contacte al banco.");
    }
}
