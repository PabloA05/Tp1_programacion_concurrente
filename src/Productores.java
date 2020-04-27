import java.util.concurrent.ThreadLocalRandom;

public class Productores implements Runnable {
    private Buffer buffer;

    public Productores(Buffer buffer) {
        this.buffer = buffer;
    }

    private void cocinar() throws InterruptedException {
        if(buffer.num==1000)return;

        int rand = ThreadLocalRandom.current().nextInt(1, 150 + 1);
        Thread.sleep(rand);
    }

    @Override
    public void run() {

        while ((buffer.num<1000)) {
            try {
                cocinar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buffer.reposition(ThreadLocalRandom.current().nextInt((1)));
        }
        Thread.currentThread().interrupt();

    }
}