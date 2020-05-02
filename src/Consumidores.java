public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {
        while ((buffer.num < 1000)) {
            try {
                int sleep = buffer.consume();
                if (sleep != 0) {
                    Thread.sleep(sleep);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " TERMINO");
    }
}