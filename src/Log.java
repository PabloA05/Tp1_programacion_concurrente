import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Log implements Runnable {

    private Buffer buffer;
    private Thread[] consumerThread;
    int cantidad;
    String filepath = "log.csv";
    private int segundos=0;


    public Log(Buffer buffer, Thread consumerThread[], int cantidad_consumidores) {
        this.buffer = buffer;
        this.consumerThread = consumerThread;
        cantidad = cantidad_consumidores;

    }

    private static ThreadLocal<Date> starDate = new ThreadLocal<Date>() { //no anda
        protected Date InitialValue() {
            return new Date();
        }
    };

    @Override
    public void run() {
        try {
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.printf("\"Segundos\"" + "," + "\"Buffer\"");
            for (int i = 0; i < cantidad; i++) {
                pw.printf("," + '"' + consumerThread[i].getName() + '"');
            }
            pw.println();

            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                segundos+=2;
                System.out.println("El buffer posee:" + buffer.store_queue.size()+" elementos.");
                for (int i = 0;i<2;i++){
                    System.out.println("El estado de "+ consumerThread[i].getName() + " es " + consumerThread[i].getState() + "\n");
                }

                pw.printf(segundos + "," + buffer.store_queue.size());
                for (int i = 0; i < cantidad; i++) {

                    pw.printf("," + consumerThread[i].getState());
                }
                pw.println();
                pw.flush();
                if (buffer.num == 1000) {
                    break;
                }
            }
            pw.close();
            System.out.println("Ejecución Finalizada");
            System.out.println(buffer.num);
            System.out.println("El buffer posee: "+ buffer.store_queue.size()+ " elementos.");
            for (int i = 0;i<2;i++){
                System.out.println("El estado de "+ consumerThread[i].getName() + " es " + consumerThread[i].getState());
            }
            System.out.println("\n");
            }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            Thread.interrupted(); //no llega aca
        }


    }
}