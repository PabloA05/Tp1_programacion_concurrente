import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    public int num;
    private ReentrantLock lockQueue;
    private Condition call;
    private final int capacity = 25;
    public Queue<Integer> store_queue = new LinkedList<Integer>();

    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);
        call = lockQueue.newCondition();
    }

    public void reposition(int producto) {
        lockQueue.lock();
        if (num != 1000) {
            if (store_queue.size() == capacity) store_queue.clear();

            store_queue.add(producto);
            System.out.printf("Tamano de la lista en store:%s\n", store_queue.size());
            System.out.println(Thread.currentThread().getName() + " entro a reposition, con el numero: " + producto);
            System.out.println();
            call.signalAll();
        }
        lockQueue.unlock();
    }

    public int consume() {
        lockQueue.lock();
        while (store_queue.isEmpty() && num != 1000) {
            try {
                call.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (num == 1000) return 0;
            else {
                System.out.println(Thread.currentThread().getName() + " entro a consume, con el numero: " + store_queue.peek());
                num++;
                System.out.println("Cantidad consumida " + num);
                return (store_queue.poll());
            }
        } finally {
            lockQueue.unlock();
        }
    }
}
