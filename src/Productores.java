import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Productores implements Runnable {
    private Buffer buffer;


    private LinkedList<Producto> list_products = new LinkedList<>();

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }

    private void cocinar() {
        int rand = ThreadLocalRandom.current().nextInt(1, 200 + 1);
        this.producto_add(new Producto(rand));
        try {
            wait(rand); // espera rand entre 1 y 200 ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void producto_add(Producto producto) {
        list_products.add(producto);
    }

    private void discard() {
        list_products.remove();
    }


    @Override
    public void run() {

    }
}

