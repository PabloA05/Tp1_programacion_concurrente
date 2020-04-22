import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {


    public int num;
    //private ReentrantLock lockQueue;
    private Condition call;

    private ReentrantLock prodLock;
    private ReentrantLock consLock;

    final int capacity = 25;
    private boolean vacio;
    private final Semaphore cenaforo = new Semaphore(1, true); //el semaforo es el unico que permite la edicion de la lista
    public LinkedBlockingQueue<Integer> store_queue = new LinkedBlockingQueue<Integer>(capacity);


    public Buffer(boolean fairMode) {
        //lockQueue = new ReentrantLock(fairMode);
        //call = lockQueue.newCondition();
        vacio = false;
        prodLock = new ReentrantLock(fairMode);
        consLock = new ReentrantLock(fairMode);
    }

    /*private void remove_fromStore() {
       store_queue.remove();
    }*/
    private void set(int productores_list) {
        try {
            try {
                cenaforo.acquire();
            } catch (InterruptedException e) {
                System.out.println("*** Problema con el semaforo ***");
                e.printStackTrace();
            }
            store_queue.offer(productores_list);
            System.out.printf("Tamano de la lista en store:%s\n", store_queue.size());
            System.out.println(Thread.currentThread().getName() + " entro a reposition, con el numero: " + productores_list);
            System.out.println();
        } catch (NullPointerException e) {
            System.out.println(Thread.currentThread().getName() + "*** No se cargo nada en reposition ***");
        } finally {
           // notifyAll();
            cenaforo.release();
        }
    }


    public void reposition(int productores_list) throws LimiteException {
       /* lockQueue.lock();

        if (store_queue.peek() == null) {
            vacio = true;
        }

        if (store_queue.size() >= capacity) {// Aca se limita la entrada al resto del metodo es suporfluo en realidad, sin esto igual no carga la lista
            lockQueue.unlock();                 // Se hizo asi por si se queria que siga produciendo si el lock estaba ocupado, pero no se implemento porque no se pide explicitamente en el tp.
            throw new LimiteException(false);   // No llega a tocar store_queue y elimana todos los elementos de la list de produccion
        } else {
            try {
                //System.out.printf("Tamano de la lista en store:%s\n", store_queue.size());
                //System.out.println(Thread.currentThread().getName() + " entro a reposition, con el numero: " + productores_list);
                //System.out.println();
                store_queue.offer(productores_list);
                if (vacio == true) {
                    call.signalAll();

                }
            } catch (NullPointerException e) {
                System.out.println("No se cargo nada en reposition");
            } finally {
                lockQueue.unlock();
            }
        }*/

        prodLock.lock();
        if (store_queue.size() >= capacity) {// Aca se limita la entrada al resto del metodo es suporfluo en realidad, sin esto igual no carga la lista
            prodLock.unlock();                 // Se hizo asi por si se queria que siga produciendo si el lock estaba ocupado, pero no se implemento porque no se pide explicitamente en el tp.
            throw new LimiteException(false);   // No llega a tocar store_queue y elimana todos los elementos de la list de produccion
        }
        /*else if (store_queue.isEmpty()) {
            cenaforo.acquireUninterruptibly();
            set(productores_list);
            prodLock.unlock();
        }*/
        else {
            set(productores_list);
            prodLock.unlock();
        }
    }


    private void get() {

        cenaforo.release();
    }

    public void consume() {
       /* lockQueue.lock();
        boolean vacio = false;
        try {
            while (store_queue.peek() == null) { // en 3 prod y 5 consumidores, entra con la lista vacia
                try {
                    vacio = true;  //esta vacia la cola
                    call.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (num <= 1000 && !(store_queue.peek() == null)) {
                num++;
                System.out.println(Thread.currentThread().getName() + " entro a consume, con el numero: " + store_queue.peek());
                System.out.println("Esta vacia? " + store_queue.isEmpty() + " tamanio store_queue: " + store_queue.size());
                System.out.printf("Consumos %s\n", num);
                call.signal();
                lockQueue.unlock();

                return store_queue.poll();
            } else if (vacio == true && store_queue.peek() == null) {//no se porque me dice de simplificar esto
                System.out.println("dentro del if lista vacia, Mal!");

                lockQueue.unlock();
                return -9999999;
            }

        } catch (NullPointerException e) {
            System.out.println("lista vacia, Mal!, entro al catch de consume()");
            e.printStackTrace();
            throw new NullPointerException();


        }finally {
            lockQueue.unlock();
        }*/
       /* lockQueue.lock();
        try {
            while (store_queue.peek() == null) {
                try {
                    call.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (num==1000){
                System.out.println("Llego a 1000 *********************************************************************");
                return 0;
            }

            System.out.println(Thread.currentThread().getName() + " entro a consume, con el numero: " + store_queue.peek());
            System.out.println("vacia? " + store_queue.isEmpty());

            if (store_queue.peek() == null) {
                System.out.println("dentro del if lista vacia, Mal!");
                lockQueue.unlock();
                return -9999999;
            }
            if (num <= 1000) {
                num++;
            }
            lockQueue.unlock();

            return store_queue.poll();
        } catch (NullPointerException e) {
            System.out.println("lista vacia, Mal!, entro al catch de consume()");
            throw new NullPointerException();
        }*/
        //consLock.lock();


    }


}