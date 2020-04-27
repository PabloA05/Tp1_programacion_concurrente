public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {
        while ((buffer.num < 1000)) {
            try {
                buffer.consume();
            } catch (NullPointerException e) {
                System.out.println("Algo NULL en buffer.consume(), mal!");
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " TERMINO");
    }
}