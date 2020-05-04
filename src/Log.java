import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log implements Runnable {

    private Buffer buffer;
    private Thread[] consumerThread;
    private String threadState;
    private int cantidad;
    String filepath = "log.csv";
    //private Date date;
    //java.util.Date test_date=new Date();
    //private int Iteraciones = 0;

    public Log(Buffer buffer, Consumidores[] consumidores, int consumideres_cantidad) {
        this.buffer = buffer;
        this.consumerThread = consumidores;
        cantidad = consumideres_cantidad;
    }


    /*public Log(Buffer buffer, Thread[] consumerThread, int cantidad_consumidores) {
        this.buffer = buffer;
        this.consumerThread = consumerThread;
        cantidad = cantidad_consumidores;
        //date=new Date();
    }*/

    /*  private static ThreadLocal<Date> starDate = new ThreadLocal<Date>() { //no anda
          protected Date InitialValue() {
              return new Date();
          }
      };*/
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

           //pw.printf("\"Iteraciones\"" + "," + "\"Buffer\"" + "," + "\"Cantidad \"");
            pw.printf( "\"Buffer\"" + "," + "\"Cantidad \"");
            for (int i = 0; i < cantidad; i++) {

                pw.printf("," + '"' + consumerThread[i].getName() + '"');
            }
            pw.println();

            while (buffer.num != 1000) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Iteraciones++;
                //pw.printf(Iteraciones + "," + buffer.store_queue.size() + "," + buffer.num);
                pw.printf(buffer.store_queue.size() + "," + buffer.num);
                for (int i = 0; i < cantidad; i++) {
                    threadState = consumerThread[i].getState().toString();
                    if (threadState.equals("TIMED_WAITING")) {
                        pw.printf("," + "Consumiendo");
                    } else {
                        pw.printf("," + "Disponible");
                    }
                }
                pw.println();
                pw.flush();
            }
            pw.close();

            //JOptionPane.showMessageDialog(null, "Guardado");
        } catch (
                IOException e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "No guardado");
        } finally {
            System.out.println("LOG en la carpeta.");
        }
        long stopTime = System.currentTimeMillis();
        System.out.printf("Tiempo transcurrido: %s segundos.\n", (stopTime - startTime) / 1000);
    }
}