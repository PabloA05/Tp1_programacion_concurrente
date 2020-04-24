import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Productores implements Runnable {
    private Buffer buffer;
    private LinkedList<Producto> list_products = new LinkedList<>();


    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }


    private void producto_add(Producto producto) {
        list_products.add(producto);
    }

    public Producto head_list_products() {
        return list_products.getFirst();
    }

    private void cocinar() throws InterruptedException {
        if(buffer.num==1000)return;

        int rand = ThreadLocalRandom.current().nextInt(1, 150 + 1);
        this.producto_add(new Producto(rand));
        Thread.sleep(rand); //aca duerme
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    @Override
    public void run() {

        while ((buffer.num<1000)){
            try {
                cocinar();
                if(buffer.num==1000)break;
                try {
                    buffer.reposition(head_list_products().get_product()); //Le estoy pasando un numero
                    if(buffer.num==1000)break;
                    list_products.remove(); //Lo hice asi porque podria hacer que los productores sigan produciendo
                } catch (LimiteException e) {
                    list_products.clear();
                    System.out.println("Se elimino todo de: " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                System.out.println("Paso algo muy malo en el run() de Productores");
            }

        }
        System.out.println(Thread.currentThread().getName()+" TERMINO");
        Thread.currentThread().interrupt();

    }
}