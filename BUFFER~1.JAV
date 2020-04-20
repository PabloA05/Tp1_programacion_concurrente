//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Math;

/**
 * Clase Buffer
 */
public class Buffer {
	private final LinkedList<String> buffer;
	private final int maxSize;
	private final ReentrantLock lock;
	private final Condition lines;
	private final Condition space;
	private int contadorSuma;
	private int contadorResta;

	/**
	 * Constructor del Buffer
	 * @param maxSize es la máxima capacidad del Buffer
	 */
	public Buffer(int maxSize) {
		this.maxSize = maxSize;
		this.buffer = new LinkedList();
		this.lock = new ReentrantLock();
		this.lines = this.lock.newCondition();
		this.space = this.lock.newCondition();
		contadorSuma =0 ;
		contadorResta = 0;
	}

	/**
	 * Permite a los productores ingresar su producto en el Buffer con un retraso aleatorio determinado en el processLine
	 * @param line Simula un producto
	 */
	public void insert(String line) {
		if (lock.tryLock()) {
			this.lock.lock();

			try {
				while (this.buffer.size() == this.maxSize) {
					this.space.await();
				}
				processLine();
				this.buffer.offer(line);
				contadorSuma++;
				this.lines.signalAll();
			} catch (InterruptedException var6) {
				var6.printStackTrace();
			} finally {
				this.lock.unlock();
			}

		}
	}

	/**
	 * Permite a los consumidores retirar un producto, elimina la cabecera del Buffer
	 */
	public void get() {
		if (lock.tryLock()) {
			this.lock.lock();

			try {
				while (this.buffer.size() == 0 ) {
					this.lines.await();
				}
					processLine();
					this.buffer.poll();
					contadorResta++;
					this.space.signalAll();

			} catch (InterruptedException var6) {
				var6.printStackTrace();
			} finally {
				this.lock.unlock();
			}
		}
	}

	/**
	 * Duerme al hilo que se esta ejecutanto una cantidad aleatoria de tiempo entre 30 y 60 ms
	 */
	private void processLine() {
		try {
			int aleatorio = (int) (Math.random() * 11) + 1;
			Thread.sleep((30 + 3 * aleatorio ));
		} catch (InterruptedException var3) {
			var3.printStackTrace();
		}

	}

	/**
	 *
	 * @return Devuelve el tamaño del Buffer
	 */
	public int relleno() {
		return buffer.size();
	}

	/**
	 *
	 * @return Devuelve la cantidad de productos ingresados por los productores
	 */
	public int valorSuma() {
		return contadorSuma;
	}

	/**
	 *
	 * @return Devuelve la cantidad de veces que los consumidores retiraron un producto
	 */
	public int valorResta(){
		return contadorResta;
	}
}
