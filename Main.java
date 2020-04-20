package com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.core;

import com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task.Buffer;
import com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task.Consumer;
import com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task.Log;
import com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task.Producer;

/**
 * Clase Main
 *
 */
public class Main {

	/**
	 * Constructor del Main
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		/**
		 * Crea un buffer con un capacidad máxima de 25
		 */
		Buffer buffer = new Buffer(25);


		/**
		 * Crea productores y los hilos para ejecutarlos
		 */
		Producer producers[] = new Producer[3];
		Thread producersThread[] = new Thread[3];
		for(int i= 0;i<3;i++){
			producers[i]= new Producer(buffer);
			producersThread[i] = new Thread(producers[i], "Producer "+ i);

		}

		/**
		 * Crea consumidores y los hilos para ejecutarlos
		 */
		Consumer consumers[] = new Consumer[3];
		Thread consumersThreads[] = new Thread[3];

		for (int i = 0; i < 3; i++) {
			consumers[i] = new Consumer(buffer);
			consumersThreads[i] = new Thread(consumers[i], "Consumer " + i);
		}


		/**
		 * Crea un Log y un hilo para ejecutarlo
		 */
		Log log = new Log( buffer,consumersThreads,producersThread);
		Thread logThread = new Thread(log, "Log");


		/**
		 *  Ejecuta el Log, los productores y los consumidores
		 */
		logThread.start();
		producersThread[0].start();
		for (int i = 0; i < 3; i++) {
			consumersThreads[i].start();
		}
	}

}
