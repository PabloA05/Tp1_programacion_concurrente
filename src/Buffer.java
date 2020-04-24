import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {


    public int num;
    private ReentrantLock lockQueue;
    public Condition call;
    final int capacity = 25;

    public LinkedBlockingQueue<Integer> store_queue = new LinkedBlockingQueue<Integer>(capacity);


    public Buffer(boolean fairMode) {
        lockQueue = new ReentrantLock(fairMode);
        call = lockQueue.newCondition();
    }

    private void set(int productores_list) {
        if (num == 1000) {
            lockQueue.unlock();
            return;
        }
        try {
            store_queue.offer(productores_list);
            System.out.printf("Tamano de la lista en store:%s\n", store_queue.size());
            System.out.println(Thread.currentThread().getName() + " entro a reposition, con el numero: " + productores_list);
            System.out.println();
        } catch (NullPointerException e) {
            System.out.println(Thread.currentThread().getName() + "No se cargo nada en reposition");
        }
    }


    public void reposition(int productores_list) throws LimiteException {
        if (num == 1000) return;

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
            throw new LimiteException(false);
        } else {
            try {
                set(productores_list);
                if (num == 1000) {
                    lockQueue.unlock();
                    return;
                }

            } catch (NullPointerException e) {
                System.out.println(Thread.currentThread().getName() + "*** No lo pude adquirir en repostion ***");
                e.printStackTrace();
            } finally {
                call.signalAll();
                lockQueue.unlock();
            }
        }
    }

    private void get(boolean consumiendo) {
        if (num == 1000) return;
        else {
            try {
                if (store_queue.isEmpty() && (num == 1000 || num == 0)) {
                    System.out.println("vacio, No tendria que pasar ");
                } else {
                    System.out.println(Thread.currentThread().getName() + " entro a consume, con el numero: " + store_queue.peek());
                    System.out.println("vacia? " + store_queue.isEmpty());
                    Thread.sleep(store_queue.poll());
                    num++;
                    System.out.println("Cantidad " + num);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " *** valor nulo en lista, muy malo ***");
                e.printStackTrace();
            } finally {
                call.signalAll();
            }
        }
    }

    public void consume(boolean consumiendo) {
        lockQueue.lock();
        if (num == 1000) {
            lockQueue.unlock();
            return;
        }
        try {
            while (store_queue.isEmpty() && num != 1000) {
                try {
                    /*if (num == 1000) {
                        continue;
                    } else */call.await();
                } catch (InterruptedException e) {
                    System.out.println("*** pasa algo que el wait, help ***");
                    e.printStackTrace();
                }
            }
            try {
                get(consumiendo);
            } catch (NullPointerException e) {
                System.out.println("*** problemas con el acquiere de consume");
                e.printStackTrace();
            }
        } finally {
            try {
               /* System.out.println("nombre " + Thread.currentThread().getName());
                System.out.println("getWaitQueueLength() "+ lockQueue.getWaitQueueLength(call));
                System.out.println("getQueueLength() " + lockQueue.getQueueLength());
                System.out.println(" \thasQueuedThreads() "+lockQueue.hasQueuedThreads());
                System.out.println("hasWaiters "+lockQueue.hasWaiters(call));
                System.out.println("isHeldByCurrentThread() "+lockQueue.isHeldByCurrentThread());
                System.out.println();*/
                lockQueue.unlock();
            } catch (IllegalMonitorStateException e) {
                System.out.println("no lo entiendo este error"); // para verlo sacar el try y catch, creo que es porque hago lockQueue.unlock(); cuando no hay nada lockeado
                // e.printStackTrace();
            }
        }
    }
}