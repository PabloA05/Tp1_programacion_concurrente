public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Productores productor = new Productores(buffer);
        Consumidores consumidor = new Consumidores(buffer);

        productor.start();
        consumidor.start();

    }
}