public class Consumidores implements Runnable {
    private Buffer buffer;
    private Boolean consumiendo=false;

    public Consumidores(Buffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {
       while ((buffer.num<1000)){
            try {
                if (buffer.num==1000){
                    System.out.printf("Llego a %s",buffer.num);
                    break;
                }
                buffer.consume(consumiendo);
                if (buffer.num==1000){
                    //System.out.printf("Llego a %s",buffer.num);
                    //buffer.call.signalAll();
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println("Algo NULL en buffer.consume(), mal!");
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+" TERMINO");
    }
}