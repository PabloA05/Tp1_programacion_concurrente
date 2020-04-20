//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task;

import java.util.Random;

public class Consumer implements Runnable {
	private Buffer buffer;

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}


	public void run() {
		while(buffer.valorResta()<1000) {
			this.buffer.get();
			System.out.println("Resta: "+ buffer.valorResta());
		}

	}
}
