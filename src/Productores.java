import java.util.Vector;
import java.util.Random;

public class Productores implements Runnable {
    private Buffer buffer;
    private static final int tam = 25; //capacidad del buffer
    private final Vector<Integer> elementos = new Vector();
    Random numeros_aleatorios = new Random();
    int datos;

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }

    private synchronized void cocinar() throws Exception {
        if (elementos.size() == tam)
            elementos.clear();//ya que el sistema es pura pérdida al estar lleno el buffer descartamos todo
        else {
            datos = numeros_aleatorios.nextInt(10);//genero numeros aleatorios del 0 al 9
            elementos.add(datos);//agrego los elementos al vector
        }
    }

    public void run() {

    }
}

