public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {


        int valor;



            valor = buffer.consumir();



    }
}
