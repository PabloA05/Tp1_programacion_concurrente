public class Buffer {
    private int contenido;
    private boolean disponible = false;


    public synchronized int consumir() {
        while (!disponible) {
            try {
                wait();
            } catch (InterruptedException excepcion) {
            }

        }
        disponible = false;
        notifyAll();
        return contenido;
    }

    public synchronized void producir(int valor) {
        while (disponible) {
            try {
                wait();
            } catch (InterruptedException excepcion) {
            }
        }
        contenido = valor;
        disponible = true;
        notifyAll();
    }
}


