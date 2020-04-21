public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(buffer.consume());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Algo NULL en buffer.consume(), mal!");
            e.printStackTrace();
        }
    }
}
