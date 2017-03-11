package com.kluyuko.andrey.calculator;

import java.util.List;

/**
 * Class to calculate constants depending on user input
 * 
 * 
 */
public class Calculator {

	private List<Double> constants;
	private List<Double> x;
	private List<Double> y;
	public final static double E = 1;

	@SuppressWarnings("unused")
	private Calculator() {
	}

	public Calculator(List<Double> x, List<Double> y) {
		this.x = x;
		this.y = y;
	}

	public List<Double> getConstants() {
		return constants;
	}

	public void setConstants(List<Double> constants) {
		this.constants = constants;
	}

	public List<Double> getX() {
		return x;
	}

	public void setX(List<Double> x) {
		this.x = x;
	}

	public List<Double> getY() {
		return y;
	}

	public void setY(List<Double> y) {
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

	public List<Double> calculateConstants() {

		int counter = 0;
		int numberOfLines = x.size();
		double arr[][] = new double[numberOfLines][numberOfLines];

		/* Calculating arr[][] using radial function */
		for (int i = 0; i < numberOfLines; i++) {
			for (int j = 0; j < numberOfLines; j++) {
				arr[i][j] = fi(radius(x.get(i), x.get(j)), E);
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("Y= ");
		for (int i = 0; i < numberOfLines; i++) {
			System.out.println(y.get(i));
		}

		/* Algorithm go ahead */
		while (counter < numberOfLines) {
			double temp = arr[counter][counter];
			double temp3 = y.get(counter);
			for (int i = counter; i < numberOfLines - 1; i++) {
				double temp2 = arr[i + 1][counter];
				for (int j = counter; j < numberOfLines - 1; j++) {
					arr[i + 1][j + 1] = arr[i + 1][j + 1] - arr[counter][j + 1] * temp2 / temp;
				}
				y.set(i + 1, y.get(i + 1) - temp3 * temp2 / temp);
			}
			y.set(counter, y.get(counter) / temp);
			for (int i = counter; i < numberOfLines; i++) {
				arr[counter][i] = arr[counter][i] / temp;
			}
			for (int i = counter + 1; i < numberOfLines; i++) {
				arr[i][counter] = 0;
			}
			counter++;
		}

		/* Algorithm go behind */
		x.set(numberOfLines - 1, y.get(numberOfLines - 1));
		for (int i = numberOfLines - 2; i >= 0; i--) {
			double temp = 0;
			for (int j = i + 1; j < numberOfLines; j++) {
				temp += arr[i][j] * x.get(j);
			}
			x.set(i, y.get(i) - temp);
		}
		System.out.println("c[i] : ");
		for (int i = 0; i < numberOfLines; i++) {
			System.out.println(x.get(i));
		}
		return x;
	}

	public double approximate(List<Double> c, double point) {
		double sum = 0;
		double fi = 0;
		for (int i = 0; i < c.size(); i++) {
			fi = fi(radius(point, x.get(i)), E);
			System.out.println("fi(i) = " + fi);
			sum += c.get(i) * fi;
		}
		return sum;
	}

}
