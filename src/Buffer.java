import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {


    public int num;
    private ReentrantLock lockQueue;
    private Condition call;
    int capacity = 25;
    public LinkedBlockingQueue<Integer> store_queue = new LinkedBlockingQueue<Integer>(capacity);


    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);
        call = lockQueue.newCondition();
    }

    /*private void remove_fromStore() {
       store_queue.remove();
    }*/
    public int consume() {

        lockQueue.lock();
        try {
            while (store_queue.peek() == null) {
                try {
                    call.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(num==1000){
                System.out.println("goal: "+num);
            }
            if (num <= 1000) {
                num++;
            }
            //System.out.println(Thread.currentThread().getName() + " entro a consume, con el numero: " + store_queue.peek());
            //System.out.println("vacia? " + store_queue.isEmpty());
            lockQueue.unlock();
            if (store_queue.peek() == null) {
                System.out.println("dentro del if lista vacia, Mal!");
                return -9999999;
            }
            return store_queue.poll();
        } catch (NullPointerException e) {
            System.out.println("lista vacia, Mal!, entro al catch de consume()");
            throw new NullPointerException();
        }
    }

    public void reposition(int productores_list) throws LimiteException {
        lockQueue.lock();
        boolean vacio = false;
        if (!(store_queue.peek() == null)) {
            vacio = true;
        }
        if (store_queue.size() >= capacity) {// Aca se limita la entrada al resto del metodo, sin esto igual no carga la lista
            lockQueue.unlock();
            throw new LimiteException(false); //No llega a tocar store_queue y elimana todos los elementos de la list de dproduccion
        } else {
            try {
                System.out.printf("Tamano de la lista en store:%s\n", store_queue.size());
                //System.out.println(Thread.currentThread().getName() + " entro a reposition, con el numero: " + productores_list);
                //System.out.println();
                store_queue.offer(productores_list);
                if (!vacio) {
                    call.signalAll();
                }
            } catch (NullPointerException e) {
                System.out.println("No se cargo nada en reposition");
            } finally {
                lockQueue.unlock();

            }
        }
    }
}