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
           // System.out.println(a);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("entra en el catch de consumidores");
        }

    }
}
