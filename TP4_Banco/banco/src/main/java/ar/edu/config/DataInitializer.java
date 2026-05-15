public class DataInitializer {
    private final CajaAhorro cajaAhorro;

    public DataInitializer(CajaAhorro cajaAhorro) {
        this.cajaAhorro = cajaAhorro;
    }
    @PostConstruct
    publlic void init() {
        Cliente cliente1 = new Cliente("Juan", "Perez", "12345678");
        Cliente cliente2 = new Cliente("Maria", "Gomez", "87654321");

        cajaAhorro.agregarCajaAhorro(new CajaAhorro("CBU123", 1000.0, cliente1));
        cajaAhorro.agregarCajaAhorro(new CajaAhorro("CBU456", 2000.0, cliente2));
    }
}
