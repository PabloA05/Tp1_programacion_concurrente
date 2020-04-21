
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {


    public int num;
    private Lock lockQueue;
    int capacity = 25;
    public LinkedBlockingQueue<Integer> store_queue = new LinkedBlockingQueue<Integer>(capacity);


    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);

        //cola con finita cantidad
    }


    //private void remove_fromStore() {
    //   store_queue.remove();
    //}

    public int consume(Consumidores consumidores) {
        lockQueue.lock();
        try {
            if (!store_queue.isEmpty()) {
                synchronized (this) {
                    num++;
                }
                System.out.printf(Thread.currentThread().getName() + " numero en el try %s\n", num);

                System.out.println(store_queue.isEmpty());

                //return store_queue.poll();
            }
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }


        //for (int i = 0; i < 10; i++) {
        System.out.println(Thread.currentThread().getName() + " largo comida: " + store_queue.size());
        //System.out.println(store_queue.peek().get_product());
        System.out.println(Thread.currentThread().getName() + " esta vacia comida? " + store_queue.isEmpty());
        //}
        lockQueue.unlock();

        return -9999;

    }

    public void reposition(int productores_list) throws LimiteException {
        lockQueue.lock();
        if (store_queue.size() >= capacity) {
            lockQueue.unlock();
            throw new LimiteException(false);

        } else {
            try {
                System.out.printf("Tamano de la lista en store:%s\n", store_queue.size());
                //System.out.println(Thread.currentThread().getName() + " entro a reposition, con el numero: " + productores_list);
                //System.out.println();
                store_queue.offer(productores_list);

            } catch (NullPointerException e) {
                System.out.println("No se cargo nada en reposition");
            } finally {
                lockQueue.unlock();
            }
        }
    }
}


