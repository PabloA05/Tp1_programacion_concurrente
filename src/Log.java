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
    String filepath = "log.txt";
    //private Date date;
    //java.util.Date test_date=new Date();
    private int segundos=0;


    public Log(Buffer buffer, Thread consumerThread[], int cantidad_consumidores) {
        this.buffer = buffer;
        this.consumerThread = consumerThread;
        cantidad = cantidad_consumidores;
        //date=new Date();

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
            JOptionPane.showMessageDialog(null, "Guardado");


        } catch (
                IOException e) {
            JOptionPane.showMessageDialog(null, "No guardado");
        }finally {
            Thread.interrupted(); //no llega aca
        }
    }
}