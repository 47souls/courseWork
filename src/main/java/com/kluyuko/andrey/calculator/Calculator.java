package com.kluyuko.andrey.calculator;

import java.util.ArrayList;
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

	// radius function

	public double radius(double begin, double end) {
		return Math.abs(begin - end);
	}

	// Different types of radial function

	public double multiQuadroRadialFunction(double radius, double e) {
		return Math.pow(1 + Math.pow(radius * e, 2), 0.5);
	}

	public double revertedMultiQuadroRadialFunction(double radius, double e) {
		return 1.0 / multiQuadroRadialFunction(radius, e);
	}

	public double revertedQuadroRadialFunction(double radius, double e) {
		return 1.0 / Math.pow(multiQuadroRadialFunction(radius, e), 0.5);
	}

	public double gaussRadialFunction(double radius, double e) {
		return Math.exp(-Math.pow(radius * e, 2.0));
	}

	// calculation specific methods

	public List<Double> calculateConstants() {

		int counter = 0;
		int numberOfLines = x.size();
		double fi[][] = new double[numberOfLines][numberOfLines];
		List<Double> oldX = new ArrayList<>();
		List<Double> oldY = new ArrayList<>();

		for (int i = 0; i < x.size(); i++) {
			oldX.add(i, x.get(i));
		}
		
		for (int i = 0; i < y.size(); i++) {
			oldY.add(i, y.get(i));
		}

		/* Calculating fi[][] using radial function */
		for (int i = 0; i < numberOfLines; i++) {
			for (int j = 0; j < numberOfLines; j++) {
				fi[i][j] = multiQuadroRadialFunction(radius(oldX.get(i), oldX.get(j)), E);
				System.out.print(fi[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("Y= ");
		for (int i = 0; i < numberOfLines; i++) {
			System.out.println(y.get(i));
		}

		/* Algorithm go ahead */
		while (counter < numberOfLines) {
			double temp = fi[counter][counter];
			double temp3 = oldY.get(counter);
			for (int i = counter; i < numberOfLines - 1; i++) {
				double temp2 = fi[i + 1][counter];
				for (int j = counter; j < numberOfLines - 1; j++) {
					fi[i + 1][j + 1] = fi[i + 1][j + 1] - fi[counter][j + 1] * temp2 / temp;
				}
				oldY.set(i + 1, oldY.get(i + 1) - temp3 * temp2 / temp);
			}
			oldY.set(counter, oldY.get(counter) / temp);
			for (int i = counter; i < numberOfLines; i++) {
				fi[counter][i] = fi[counter][i] / temp;
			}
			for (int i = counter + 1; i < numberOfLines; i++) {
				fi[i][counter] = 0;
			}
			counter++;
		}

		/* Algorithm go behind */
		oldX.set(numberOfLines - 1, oldY.get(numberOfLines - 1));
		for (int i = numberOfLines - 2; i >= 0; i--) {
			double temp = 0;
			for (int j = i + 1; j < numberOfLines; j++) {
				temp += fi[i][j] * oldX.get(j);
			}
			oldX.set(i, oldY.get(i) - temp);
		}
		System.out.println("c[i] : ");
		for (int i = 0; i < numberOfLines; i++) {
			System.out.println(oldX.get(i));
		}
		return oldX;
	}

	public double approximate(List<Double> constants, List<Double> x, double point) {
		double sum = 0;
		double fi = 0;
		for (int i = 0; i < constants.size(); i++) {
			System.out.println("constants.get(i)" + constants.get(i));
			System.out.println("X.get(i): " + x.get(i));
			double radius = radius(point, x.get(i));
			fi = multiQuadroRadialFunction(radius, E);
			System.out.println("fi(i) = " + fi);
			sum += constants.get(i) * fi;
		}
		return sum;
	}

}
