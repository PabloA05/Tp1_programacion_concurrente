//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.packtpub.java9.concurrency.cookbook.chapter02.recipe05.task;

public class Producer implements Runnable {
	private Buffer buffer;

	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		while(buffer.valorSuma()<1000) {
			this.buffer.insert("   ");
			System.out.println("Suma: "+ buffer.valorSuma());
		}
	}

}
