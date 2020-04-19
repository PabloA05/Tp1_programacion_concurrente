import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Productores implements Runnable {
    private Buffer buffer;
    private LinkedList<Producto> list_products = new LinkedList<>();

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }

    private void cocinar() throws InterruptedException {
        int rand = ThreadLocalRandom.current().nextInt(1, 200 + 1);
        this.producto_add(new Producto(rand));

        Thread.currentThread().sleep(rand);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    private void producto_add(Producto producto) {
        list_products.add(producto);
    }

    private void discard() {
        list_products.remove();
    }

    private Producto head_list_products() {
        return list_products.pollFirst();
    }


    @Override
    public void run() {
        try {
            cocinar();
            buffer.reposition(head_list_products());
            discard();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

