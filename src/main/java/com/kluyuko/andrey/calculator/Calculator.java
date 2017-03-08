package com.kluyuko.andrey.calculator;

import java.util.List;

/**
 * Class to calculate constants depending on user input
 * 
 * 
 */
public class Calculator {

	private List<Double> constants;
	private double x[];
	private double y[];
	public static double e = 1;

	public Calculator() {
	}

	public Calculator(double x[], double y[]) {
		this.x = x;
		this.y = y;
	}

	public List<Double> getConstants() {
		return constants;
	}

	public void setConstants(List<Double> constants) {
		this.constants = constants;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double x[]) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double y[]) {
		this.y = y;
	}

	// radial function

	public double fi(double radius, double e) {
		return Math.pow(1 + Math.pow(radius * e, 2), 0.5);
	}

	// radius function

	public double radius(double begin, double end) {
		return Math.abs(begin - end);
	}

	// calculation specific methods

	public double[] calculateConstants() {

		int counter = 0;
		int numberOfLines = x.length;
		double arr[][] = new double[numberOfLines][numberOfLines];

		/* Calculating arr[][] using radial function */
		for (int i = 0; i < numberOfLines; i++) {
			for (int j = 0; j < numberOfLines; j++) {
				arr[i][j] = fi(radius(x[i], x[j]), e);
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("Y= ");
		for (int i = 0; i < numberOfLines; i++) {
			System.out.println(y[i]);
		}

		/* Algorithm go ahead */
		while (counter < numberOfLines) {
			double temp = arr[counter][counter];
			double temp3 = y[counter];
			for (int i = counter; i < numberOfLines - 1; i++) {
				double temp2 = arr[i + 1][counter];
				for (int j = counter; j < numberOfLines - 1; j++) {
					arr[i + 1][j + 1] = arr[i + 1][j + 1] - arr[counter][j + 1] * temp2 / temp;
				}
				y[i + 1] = y[i + 1] - temp3 * temp2 / temp;
			}
			y[counter] = y[counter] / temp;
			for (int i = counter; i < numberOfLines; i++) {
				arr[counter][i] = arr[counter][i] / temp;
			}
			for (int i = counter + 1; i < numberOfLines; i++) {
				arr[i][counter] = 0;
			}
			counter++;
		}

		/* Algorithm go behind */
		x[numberOfLines - 1] = y[numberOfLines - 1];
		for (int i = numberOfLines - 2; i >= 0; i--) {
			double temp = 0;
			for (int j = i + 1; j < numberOfLines; j++) {
				temp += arr[i][j] * x[j];
			}
			x[i] = y[i] - temp;
		}
		System.out.println("c[i] : ");
		for (int i = 0; i < numberOfLines; i++) {
			System.out.println(x[i]);
		}
		return x;
	}

	public double approximate(double c[], double point) {
		double sum = 0;
		double fi = 0;
		for (int i = 0; i < c.length; i++) {
			fi = fi(radius(point, x[i]), e);
			System.out.println("fi(i) = " + fi);
			sum += c[i] * fi;
		}
		return sum;
	}

}
