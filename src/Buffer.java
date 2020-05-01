import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    public int num;  //Cantidad de veces que se consumio un producto
    private ReentrantLock lockQueue; //Lock para crear zonas de exclusión mutua
    public Condition call; //Se utiliza para poner a esperar a los consumidores cuando no hay productos
    final int capacity = 25; //Capacidad de almacenamiento del Buffer (Si pasa este valor se descarta el producto)
    public LinkedList<Integer> store_queue = new LinkedList<Integer>();//Donde se guardan los productos

    //Se inicializa el objeto Buffer
    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);//Se incializa el lock en fairmode
        call = lockQueue.newCondition();//Se incializa la condicion
    }

    //Toma el lock,si se realizaron 1000 consumisiones termina el metodo,si no primero se agreaga un elemento a la lista.
    // En ambos casos luego se despierta a los consumidores que esté dormidos por falta de producto y luego suelta el lock
    public void reposition(int productores_list) {
        lockQueue.lock(); // Si está disponible toma el lock, si no se queda esperando que otro hilo lo suelte
        try{
            if (num == 1000) return; //Si ya se consumieron 100 productos se finaliza el metodo
            if (store_queue.size() >= capacity) return;//Si el buffer está lleno se termina el método y se pierde el producto
            else {
                store_queue.add(productores_list);//Se añade un producto en la cabecera de la lista
                }
         }
        finally {
                call.signalAll();//Se despiert a los hilos que estaban dormidos por falta de producto
                lockQueue.unlock();//Se libera el lock
        }
    }

    //Toma el lock,si se realizaron 1000 consumisiones termina el metodo,si no revisa si la lista está vacía.De ser así el hilo se
    //duerme hasta que un productor deposite un producto.
    //Cuando hay al menos un producto disponible toma el de la cabecera, luego duerme por un tiempo y suma 1 a la cantidad de productos
    //consumidos.
    // En ambos casos luego se despierta a los consumidores que esté dormidos por falta de producto y luego suelta el lock
    public void consume() {
        lockQueue.lock();
        try {
            if (num == 1000) return; //Si la cantidad de operaciones llega a 1000 termina el try
            while (store_queue.isEmpty() && num != 1000) {
                call.await();//Mientras el buffer esté vacio mantiene al hilo en espera, es decir hasta que un productor avise que deposito un producto
            }
            if (num == 1000) return; //Por si cuando un hilo sale del await ya se completaron las 1000 veces finaliza el metodo
            int a = store_queue.poll(); //Elimina la cabecera de la lista y almacena su valor en una variable
            Thread.sleep(a);//Duerme el hilo por un tiempo
            num++; //Aumenta en 1 la cantidad de productos consumidos

        }
        catch (InterruptedException e) {
            System.out.println(e.toString()); //Captura la excepcion en caso de que el hilo sea interrumpido en el sleep
        }
        finally {
                lockQueue.unlock();//Libera el lock
        }
    }
}