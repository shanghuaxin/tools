package com.dingcheng.user.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	private static ExecutorService executor = Executors.newFixedThreadPool(5);
	static ReentrantLock lock = new ReentrantLock();
	static int money = 10000;
	public static void main(String[] args) {
		for(int i=0; i<100; i++) {
			test(i);
		}
	}
	
	public static void test(final int i) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					lock.lock();
					money -= 10;
					System.out.println(money+"========="+i);
					Thread.sleep(1000*(i%3));
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					lock.unlock();
				}
			}
		});
	}
}
