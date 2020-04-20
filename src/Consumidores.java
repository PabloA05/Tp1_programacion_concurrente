public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {
           buffer.consume(this);
           Thread.currentThread().sleep( buffer.consume(this));

        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("entra en el catch de consumidores");
        }

    }
}
