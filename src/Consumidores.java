public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {
           Thread.currentThread().sleep( buffer.consume(this));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
