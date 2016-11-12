package com.kluyuko.andrey.graph;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.kluyuko.andrey.calculator.Calculator;

@SuppressWarnings("serial")
public class DrawGraph extends JFrame {

	private double xExpected[];
	private double yExpected[];
	private double xActual[];
	private double yActual[];
	private double c[];
	private Calculator calculator;

	public DrawGraph() {
		super("Year project");
		xExpected = new double[10];
		yExpected = new double[10];
		yActual = new double[10];
		c = new double[10];
		JPanel chartPanel = createChartPanel();
		add(chartPanel, BorderLayout.CENTER);

		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel createChartPanel() {
		String chartTitle = "Objects Movement Chart";
		String xAxisLabel = "X";
		String yAxisLabel = "Y";

		XYDataset dataset = createDataset();

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

		return new ChartPanel(chart);
	}

	private XYDataset createDataset() {

		XYSeriesCollection dataset = new XYSeriesCollection();

		// Drawing y=sin(x) graph
		XYSeries expected = new XYSeries("sin(x)");
		for (double i = 0; i < 10.0; i += 0.01) {
			expected.add(i, Math.sin(i));
		}

		calculator = new Calculator();
		// Drawing simulating points
		XYSeries actual = new XYSeries("some points");
		for (int i = 0; i < xExpected.length; i++) {
			xExpected[i] = i * 0.2;
			yExpected[i] = Math.sin(xExpected[i]);
		}
		Calculator calculator = new Calculator(xExpected, yExpected);
		c = calculator.calculateConstants();
		for (int i = 1; i <= 10; i++) {
			System.out.println("Approximated value: " + calculator.approximate(c, Math.PI / i));
		}
		// for (int i = 0; i < xExpected.length; i++) {
		// yActual[i] = calculator.approximate(c, xExpected[i]);
		// System.out.println(yActual[i]);
		// actual.add(xExpected[i],yActual[i]);
		// }

		dataset.addSeries(expected);
		// dataset.addSeries(actual);
		return dataset;
	}
}
