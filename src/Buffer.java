
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {


    public int num;
    private ReentrantLock lockQueue;
    public Condition call;
    /*    private ReentrantLock prodLock;
        private ReentrantLock consLock;
        private final Semaphore cenaforo = new Semaphore(1, true); //el semaforo es el unico que permite la edicion de la lista*/
    final int capacity = 25;
    private boolean vacio;

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
        if (store_queue.size() >= capacity) {
            /*Aca se limita la entrada al resto del metodo es suporfluo en realidad, ya que no se carga la lista si esta llena
            Se hizo asi por si se queria que siga produciendo si el lock estaba ocupado, pero no se implemento porque no se pide explicitamente en el tp.
            No llega a tocar store_queue y elimana todos los elementos de la list de produccion*/
            throw new LimiteException(false);
        }

        lockQueue.lock();


        if (num == 1000) {
            lockQueue.unlock();
            return;
        } else {
            try {
                set(productores_list);
                if (num == 1000) {
                    lockQueue.unlock();
                    return;
                }
                /*if(!store_queue.isEmpty()){
                    System.out.println("entro en is.enoty "+store_queue.isEmpty());
                    System.out.println("esperado false");
                }*/
            } catch (NullPointerException e) {
                System.out.println(Thread.currentThread().getName() + "*** No lo pude adquirir en repostion ***");
                e.printStackTrace();
            } finally {
                call.signalAll();
                lockQueue.unlock();
            }
        }
    }

    private void get() {
        if (num == 1000) {
            lockQueue.unlock();
            return;
        } else {
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

    public void consume() {
        lockQueue.lock();
        if (num == 1000) {
            lockQueue.unlock();
            return;
        }
        try {
            // System.out.println("entro algo");
            while (store_queue.isEmpty() && num != 1000) {
                try {
                    if (num == 1000) {
                        continue;
                    } else call.await();
                } catch (InterruptedException e) {
                    System.out.println("*** pasa algo que el wait, help ***");
                    e.printStackTrace();
                }
            }
            try {
                get();
            } catch (NullPointerException e) {
                System.out.println("*** problemas con el acquiere de consume");
                e.printStackTrace();
            }
        } finally {


            try {
                lockQueue.unlock();

                //System.out.println("ACA************** "+lockQueue.getQueueLength());
            } catch (IllegalMonitorStateException e) {
                System.out.println("no lo entiendo este error"); // para verlo sacar el try y catch, creo que es porque hago lockQueue.unlock(); cuando no hay nada lockeado
                // e.printStackTrace();
            }
        }
    }
}