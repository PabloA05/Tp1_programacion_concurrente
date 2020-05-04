import java.util.concurrent.ThreadLocalRandom;

public class Productores extends Thread {
    private Buffer buffer;

    private int producto;

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }


    private void cocinar() {
        if (buffer.num == 1000) return;
        //int rand=1000;
        producto = ThreadLocalRandom.current().nextInt(300, 600+ 1);
        //producto = rand;
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
