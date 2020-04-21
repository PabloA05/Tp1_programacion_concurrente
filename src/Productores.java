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
        /*for (int i = 0; i < list_products.size(); i++) {
            System.out.println(Thread.currentThread().getName()+" entro al for, entonces se cargaron los numeros, entonces concinar anda");
            System.out.println(list_products.peekFirst().get_product());
        }*/

        // Thread.currentThread().sleep(rand);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    private void producto_add(Producto producto) {
        list_products.add(producto);
    }

    public Producto head_list_products() {
        return list_products.getFirst();
    }


    @Override
    public void run() {
        try {
            cocinar();
            try {
                buffer.reposition(head_list_products().get_product()); //Le estoy pasando list_products
                list_products.remove();
            } catch (LimiteException e) {
                list_products.clear();
                System.out.println("Se elimino todo de: " + Thread.currentThread().getName());
            }


        } catch (InterruptedException e) {
            System.out.println("Paso algo malo en el run() de Productores");
        }
    }
}

