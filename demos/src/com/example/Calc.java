package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calc {

	public double suma(double a, double b) {
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return prescision(a + b);
	}

	public double resta(double a, double b) {
		return prescision(a - b);
	}

	public double divide(double a, double b) {
		if (b == 0.0)
			throw new ArithmeticException("Divide by zero");
		return prescision(a / b);
	}

	public int divide(int a, int b) {
		return a / b;
	}

	public boolean isPositive(double a) {
		return a >= 0;
	}
	
	private double prescision(double a) {
		return BigDecimal.valueOf(a)
				.setScale(16, RoundingMode.HALF_EVEN).doubleValue();
	}
}
