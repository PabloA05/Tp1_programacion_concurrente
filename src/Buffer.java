
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;

public class Buffer {


    public int num;
    private ReentrantLock lockQueue;
    public Condition call;
    final int capacity = 25;

    public LinkedList<Integer> store_queue = new LinkedList<Integer>();


    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);
        call = lockQueue.newCondition();
    }

    private void set(int productores_list) {
        try {
            store_queue.add(productores_list);
        } catch (NullPointerException e) {
            System.out.println(Thread.currentThread().getName() + "No se cargo nada en reposition");
        }
    }


    public void reposition(int productores_list) {
        try {
            lockQueue.lock();
            if (num == 1000) {
                return;
            }
            if (store_queue.size() >= capacity) {
                return;
            }
            else {
                set(productores_list);
            }
        }
        catch (NullPointerException e) {
                System.out.println(Thread.currentThread().getName() + "*** No lo pude adquirir en repostion ***");
                e.printStackTrace();
            }
        finally {
                call.signalAll();
                lockQueue.unlock();
            }
        }


    private void get() {
            try {
                if (store_queue.isEmpty() && (num == 1000 || num == 0)) {
                    System.out.println("vacio, No tendria que pasar ");
                } else {
                    Thread.sleep(store_queue.poll());
                    num++;
                }
            }
            catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " *** valor nulo en lista, muy malo ***");
                e.printStackTrace();
            }
            finally {
                call.signalAll();
            }
        }

    public void consume() {
        try {
            lockQueue.lock();
            if (num == 1000) return;
            while (store_queue.isEmpty() && num != 1000) {
                call.await();
            }
            get();
            }
        catch (NullPointerException e) {
                System.out.println("*** problemas con el acquiere de consume");
                e.printStackTrace();
            }
        catch (InterruptedException e) {
                System.out.println("*** pasa algo que el wait, help ***");
                e.printStackTrace();
        }
         finally {
                lockQueue.unlock();

        }
    }
}