public class Consumidores extends Thread {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while ((buffer.num < 1000)) {
            try {
                int producto = buffer.consume();
                if (producto != 0) {
                    Thread.sleep(producto);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " TERMINO");
    }
}