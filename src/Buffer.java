import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {


    public int num;
    private ReentrantLock lockQueue;
    public Condition call;
    final int capacity = 25;

    public LinkedList<Integer> store_queue = new LinkedList<Integer>();
    //public LinkedBlockingQueue<Integer> store_queue = new LinkedBlockingQueue<Integer>(capacity);


    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);
        call = lockQueue.newCondition();
    }

    private void set(int productores_list) {

        try {
            //store_queue.offer(productores_list);
            store_queue.add(productores_list);
            System.out.printf("Tamano de la lista en store:%s\n", store_queue.size());
            System.out.println(Thread.currentThread().getName() + " entro a reposition, con el numero: " + productores_list);
            System.out.println();
        } catch (NullPointerException e) {
            System.out.println(Thread.currentThread().getName() + "No se cargo nada en reposition");
        }
    }


    public void reposition(int productores_list) throws LimiteException {
        lockQueue.lock();

        if (num == 1000) {
            lockQueue.unlock();
            return;
        }
        if (store_queue.size() >= capacity) {
            /*Aca se limita la entrada al resto del metodo es suporfluo en realidad, ya que no se carga la lista si esta llena
            Se hizo asi por si se queria que siga produciendo si el lock estaba ocupado, pero no se implemento porque no se pide explicitamente en el tp.
            No llega a tocar store_queue y elimana todos los elementos de la list de produccion*/
            lockQueue.unlock();
            throw new LimiteException(true);
        } else {
            try {
                set(productores_list);
            } catch (NullPointerException e) {
                System.out.println(Thread.currentThread().getName() + "*** No lo pude adquirir en repostion ***");
                e.printStackTrace();
            } finally {
                call.signalAll();
                lockQueue.unlock();
            }
        }
    }

    private int get() {

        if (store_queue.isEmpty() && (num == 1000 || num == 0)) {
            System.out.println("vacio, No tendria que pasar ");
            return 0;
        } else {
            System.out.println(Thread.currentThread().getName() + " entro a consume, con el numero: " + store_queue.peek());
            System.out.println("Lista vacia?: " + store_queue.isEmpty());

            num++;
            System.out.println("Cantidad consumida " + num);
            return (store_queue.poll());

        }
    }

    public int consume() {

        lockQueue.lock();
        if (num == 1000) {

            return 0;
        }
        while (store_queue.isEmpty() && num != 1000) {
            try {
                call.await();
            } catch (InterruptedException e) {
                System.out.println("*** pasa algo que el wait, help ***");
                e.printStackTrace();
            }
        }
        try {
            return get();
        } finally {
            lockQueue.unlock();
        }
    }
}
   /* public int consume() {
        if (num == 1000) return 0;

        lockQueue.lock();
        while (store_queue.isEmpty() && num != 1000) {
            try {
                call.await();
            } catch (InterruptedException e) {
                System.out.println("*** pasa algo que el wait, help ***");
                e.printStackTrace();
            }
        }
        System.out.println("cantadidad" + num);
        num++;
        lockQueue.unlock();
        return store_queue.poll();
    }*/