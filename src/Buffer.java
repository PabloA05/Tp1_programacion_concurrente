import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {


    public int num;
    private Lock lockQueue;
    public LinkedBlockingQueue<Producto> store_queue;


    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);
        new LinkedBlockingQueue<Producto>(24); //cola con finita cantidad
    }


    //private void remove_fromStore() {
    //   store_queue.remove();
    //}

    public int consume(Consumidores consumidores) {
        lockQueue.lock();
        try {
            if (!store_queue.isEmpty()) {
                synchronized (this){
                    num++;
                }
                System.out.printf("numero en el try %s\n", num);

                System.out.println(store_queue.isEmpty());
                return store_queue.poll().get_product();
            }
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }


        //for (int i = 0; i < 10; i++) {
        System.out.println("largo comida: " + store_queue.size());
        //System.out.println(store_queue.peek().get_product());
        System.out.println("esta vacia comida? " + store_queue.isEmpty());
        //}
        lockQueue.unlock();

        return -9999;


    }

    public void reposition(Productores productores) {
        lockQueue.lock();
        try {
            System.out.println(productores.head_list_products().get_product());
            store_queue.offer(productores.head_list_products()); //tira una excepsion falsa si esta llena la cola
            Thread.currentThread().sleep(productores.head_list_products().get_product());
            productores.discard();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            productores.discard();
        } finally {
            lockQueue.unlock();
        }

        System.out.println("largo: " + store_queue.size());
        System.out.println(store_queue.peek().get_product());
        System.out.println("esta vacia? " + store_queue.isEmpty());

    }
}


