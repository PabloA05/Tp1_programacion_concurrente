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
    private int segundos = 0;


    public Log(Buffer buffer, Thread[] consumerThread, int cantidad_consumidores) {
        this.buffer = buffer;
        this.consumerThread = consumerThread;
        cantidad = cantidad_consumidores;
    }
    @Override
    public void run() {
        try {
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.printf("\"Segundos\"" + "  ,  " + "\"Buffer\"");
            for (int i = 0; i < cantidad; i++) {

                pw.printf("  ,  " + '"' + consumerThread[i].getName() + '"');
            }
            pw.println();

            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                segundos += 2;

                pw.printf("       "+segundos + "                     " + buffer.store_queue.size());
                for (int i = 0; i < cantidad; i++) {
                    threadState = consumerThread[i].getState().toString();
                    if (threadState.equals("TIMED_WAITING")) {
                        pw.printf("    ,        " + "Consumiendo");
                    } else{
                        pw.printf("    ,          " + "Disponible");
                    }
                }
                pw.println();
                pw.flush();
                if (buffer.num == 1000) {
                    break;
                }
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("LOG en la carpeta.");
            Thread.interrupted();
        }
    }
}