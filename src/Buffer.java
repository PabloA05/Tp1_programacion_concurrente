import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {

    public LinkedBlockingQueue<Producto> store_queue = new LinkedBlockingQueue<Producto>(24); //cola con finita cantidad
    public int num;
    private Lock queue_store;


    public Buffer(boolean fairMode) {
        queue_store = new ReentrantLock(fairMode);
    }


    //private void remove_fromStore() {
     //   store_queue.remove();
    //}

    public int consume(Consumidores consumidores) {
        queue_store.lock();
        try {

            num++;
            System.out.printf("numero en el try %s\n", num);



        } finally {
            queue_store.unlock();

        }
        return  store_queue.poll().get_product() ;

    }

    public void reposition(Productores productores) {
        queue_store.lock();
        try {
            store_queue.offer(productores.head_list_products()); //tira una excepsion falsa si esta llena la cola
            Thread.currentThread().sleep(productores.head_list_products().get_product());
            productores.discard();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            productores.discard();
        } finally {
            queue_store.unlock();
        }
    }
}


