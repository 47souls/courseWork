package com.kluyuko.andrey.graph;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.kluyuko.andrey.calculator.Calculator;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private double xExpected[];
	private double yExpected[];
	private double yActual[];
	private double constants[];
	private Calculator calculator;

	public MainFrame() {
		super("Year project");
		xExpected = new double[10];
		yExpected = new double[10];
		yActual = new double[10];
		constants = new double[10];

		setSize(900, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(550, 650));
		getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel graphPanel = createChartPanel();
		graphPanel.setPreferredSize(new Dimension( 350, 550));
		panel.add(graphPanel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setPreferredSize(new Dimension( 350, 100));
		panel.add(buttonsPanel);
		
		JButton drawButton = new JButton("Draw graph");
		buttonsPanel.add(drawButton);
		
		JButton removePointsButton = new JButton("Remove points");
		buttonsPanel.add(removePointsButton);
		
		JButton addPointsButton = new JButton("Add point");
		buttonsPanel.add(addPointsButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension( 350, 650));
		getContentPane().add(panel_1);
	}

	private JPanel createChartPanel() {
		String chartTitle = "Approximation by radial functions method";
		String xAxisLabel = "X";
		String yAxisLabel = "Y";

		XYDataset dataset = createDataset();

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

		return new ChartPanel(chart);
	}

	private XYDataset createDataset() {

		XYSeriesCollection dataset = new XYSeriesCollection();

		// Drawing y=sin(x) graph
		XYSeries expected = new XYSeries("y=3*x");
		for (int i = 0; i < 10.0; i++) {
			double x = i * 0.1;
			double y = 3 * x;
			expected.add(x, y);
			xExpected[i] = x;
			yExpected[i] = y;
		}

		// Calculating constants
		calculator = new Calculator(xExpected, yExpected);
		constants = calculator.calculateConstants();

		// Drawing simulating points
		XYSeries actual = new XYSeries("Approximation points");

		for (int i = 0; i < 10; i++) {
			yActual[i] = calculator.approximate(constants, xExpected[i]);
			System.out.println(yActual[i]);
			actual.add(xExpected[i], yActual[i]);
		}

		dataset.addSeries(expected);
		// dataset.addSeries(actual);

		return dataset;
	}
}
