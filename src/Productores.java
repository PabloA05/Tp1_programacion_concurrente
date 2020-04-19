import java.util.Vector;
import java.util.Random;

public class Productores implements Runnable {
    private Buffer buffer;

    private final Vector<Integer> elementos = new Vector();
    Random rand = new Random();

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }

    private synchronized void cocinar() throws Exception {


    }

    @Override
    public void run() {

    }
}

