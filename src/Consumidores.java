public class Consumidores implements Runnable {
    private Buffer buffer;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {
        while ((buffer.num < 1000)) {
            try {
                try {
                    int sleep=buffer.consume();
                    if (sleep!=0){
                        System.out.println(Thread.currentThread().getName()+" SLEEP NUMERO: "+sleep);
                        Thread.sleep(sleep);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (NullPointerException e) {
                System.out.println("Algo NULL en buffer.consume(), mal!");
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " TERMINO");
    }
}