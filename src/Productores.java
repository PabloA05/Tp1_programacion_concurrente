import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Productores implements Runnable {
    private Buffer buffer;//Se crea una variable de tipo Buffer

    //Se inicializa el objeto Productores
    public Productores(Buffer buffer) {
        this.buffer = buffer;//Se almacena el buffer en la variable
    }

    @Override
    public void run() {
        //Mientras la cantidad de operaciones sea menor a 1000 ejecuta el codigo
        while ((buffer.num < 1000)) {
            try {
                if (buffer.num == 1000) break; // Si las operaciones llegaron a 1000 termina el while
                int rand = ThreadLocalRandom.current().nextInt(1, 200 + 1); // Crea un entero aleatorio entre 1 y 200 y lo almacena en una variable
                Thread.sleep(rand);//Duerme el hilo por el valor aleatorio almacenado en la variable
                buffer.reposition(rand);//Ejecuta el metodo reposition de la clase buffer
                System.out.println(buffer.num);//Imprime por pantalla la cantidad de operaciones realizadas hasta el momento
                if (buffer.num == 1000) break;//Si las operaciones llegaron a 1000 termina el while
            }
            catch (InterruptedException e) {
                System.out.println("Se interrumpio run() de Productores");//Captura la excepcion en caso de que el hilo sea interrumpido en el sleep
            }
        }
        //Imprime por pantalla cuando termina el hilo
        System.out.println(Thread.currentThread().getName() + " TERMINO");
        //Se interrumpe la ejecución del hilo
        Thread.currentThread().interrupt();
    }
}