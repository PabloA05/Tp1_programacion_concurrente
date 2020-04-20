package com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task;

import com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.utils.FileMock;

import javax.swing.plaf.nimbus.State;
import java.sql.BatchUpdateException;

public class Log implements Runnable {

    private Buffer buffer;
    private Thread[] consumerThread;
    private Thread[] producerThread;
    public Log(Buffer buffer, Thread consumerThread[],Thread producerThread[]){
        this.buffer= buffer;
        this.consumerThread = consumerThread;
        this.producerThread = producerThread;
    }

    @Override
    public void run() {
      while(buffer.valorResta()<1000 || buffer.valorSuma()<1000){
          try {
              Thread.sleep(2000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          System.out.println("El buffer posee:" + buffer.relleno()+" elementos.");
          for (int i = 0;i<3;i++){
              System.out.println("El estado de "+ consumerThread[i].getName() + " es " + consumerThread[i].getState() + "\n");
          }
          System.out.println("El Estado de :" + producerThread[0].getName() + " es " + producerThread[0].getState() + "\n");
      }
    }
}
