import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {

        boolean fairMode = true;
        int productores_cantidad=30;

        Buffer buffer = new Buffer(fairMode);



        Thread[] productores = new Thread[productores_cantidad];
        for (int i = 0; i < productores_cantidad; i++) {
            productores[i] = new Thread(new Productores(buffer));
        }
        Thread[] consumidores = new Thread[10];
        for (int i = 0; i < 10; i++) {
            consumidores[i] = new Thread(new Consumidores(buffer));
        }


        try {
            for (int i = 0; i < productores_cantidad; i++) {
                productores[i].start();
                //try {}catch (){}
            }
           /* for (int i = 0; i < 10; i++) {
                consumidores[i].start();
                //try {}catch (){}*/
           // }
        } finally {
            Thread.interrupted();
        }



       /* if (buffer.num == 10) {
            System.out.printf("NUMERO %s\n", buffer.num);
        }// no anda
*/

      /*  while (true) {
            try {
                System.out.println("numero: "+buffer.store_queue.peek().get_product());
            } catch (NullPointerException e) {
                //e.printStackTrace();
                System.out.println("get product 0");
            }
        }*/

    }


}
