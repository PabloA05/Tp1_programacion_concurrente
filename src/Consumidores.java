import java.util.concurrent.ThreadLocalRandom;

public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
       while ((buffer.num<1000)){
            try {
                if (buffer.num==1000){
                    break;
                }
                int rand = ThreadLocalRandom.current().nextInt(1, 150 + 1);
                Thread.sleep(rand);
                buffer.consume();
                if (buffer.num==1000){
                    break;
                }

            }
            catch  (InterruptedException e) {
                System.out.println("Algo NULL en buffer.consume(), mal!");
                e.printStackTrace();
            }
        }
    }
}