import java.util.Vector;
import java.util.Random;

public class Productor extends Thread {
    private Buffer buffer;
    private static final int tam = 25; //capacidad del buffer
    private final Vector<Integer> elementos = new Vector();
    Random numeros_aleatorios = new Random();
    int datos;

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 0; i < 1000; i++) {//para generar 1000 elementos

           /* if (elementos.size() == tam)
                elementos.clear();//ya que el sistema es pura pérdida al estar lleno el buffer descartamos todo
            else {
                datos = numeros_aleatorios.nextInt(10) ;//genero numeros aleatorios del 0 al 9
                elementos.add(datos);//agrego los elementos al vector*/

            buffer.producir(cocinar());//produzco los elementos

        }


        try {
            sleep(100);


        } catch (InterruptedException excepcion) {
        }

    }


    private synchronized void cocinar() throws Exception {
        if (elementos.size() == tam)
            elementos.clear();//ya que el sistema es pura pérdida al estar lleno el buffer descartamos todo
        else {
            datos = numeros_aleatorios.nextInt(10);//genero numeros aleatorios del 0 al 9
            elementos.add(datos);//agrego los elementos al vector
        }

    }
}

