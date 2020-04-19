import java.util.Vector;
import java.util.Random;

public class Productores implements Runnable {
    private Buffer buffer;

    private final Vector<Producto> elementos = new Vector();
    Random rand = new Random();

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }

    private synchronized void cocinar() throws Exception {
        


    }

    public void run() {

    }
}

