import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Productores extends Thread {
    private Buffer buffer;

    int producto;

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }


    private void cocinar() {
        if (buffer.num == 1000) return;
        //int rand=1000;
        //this.list_products.add(rand);
        producto = ThreadLocalRandom.current().nextInt(500, 1200 + 1);
    }

    @Override
    public void run() {
        while ((buffer.num < 1000)) {
            cocinar();
            try {
                Thread.sleep(producto); //aca duerme
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buffer.reposition(producto);
        }
        System.out.println(Thread.currentThread().getName() + " TERMINO");
    }
}
/*
    public void run() {

        while ((buffer.num < 1000)) {
            cocinar();
            boolean check = buffer.reposition(head_list_products()); //Le estoy pasando un numero
            if (buffer.num == 1000) break;
            if (check) {
                System.out.println("se borro todo");
                list_products.clear();
            } else
                list_products.remove(); //Lo hice asi porque podria hacer que los productores sigan produciendo pero no lo pide el tp asi que no lo inclui;
        }
        System.out.println(Thread.currentThread().getName() + " TERMINO");
        Thread.currentThread().interrupt();
    }
}*/
