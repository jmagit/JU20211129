package com.example;

public class Calc {

	public double suma(double a, double b) {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return a + b;
	}

	public double resta(double a, double b) {
		return a - b;
	}

	public double divide(double a, double b) {
		if (b == 0.0)
			throw new ArithmeticException("Divide by zero");
		return a / b;
	}

	public int divide(int a, int b) {
		return a / b;
	}

	public boolean isPositive(double a) {
		return a >= 0;
	}
}
