package com.kluyuko.andrey.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.kluyuko.andrey.calculator.Calculator;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private double xExpected[];
	private double yExpected[];
	private double yActual[];
	private double constants[];

	// points dataSet
	private XYDataset dataset;
	private XYSeries actual;
	private XYSeries expected;

	private Calculator calculator;
	private JTextField xValueTextField;
	private JTextField yValueTextField;

	// Panel definition

	private JPanel graphPanel;

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

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(650, 650));
		getContentPane().add(leftPanel);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		graphPanel = createChartPanel();
		graphPanel.setPreferredSize(new Dimension(350, 550));
		leftPanel.add(graphPanel);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setPreferredSize(new Dimension(350, 100));
		leftPanel.add(buttonsPanel);

		JButton drawButton = new JButton("Draw graph");
		buttonsPanel.add(drawButton);

		JButton removePointsButton = new JButton("Remove points");
		removePointsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Something happened!");
				expected.clear();
			}
		});
		buttonsPanel.add(removePointsButton);

		JButton addPointsButton = new JButton("Add point");
		buttonsPanel.add(addPointsButton);

		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setPreferredSize(new Dimension(250, 650));
		getContentPane().add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		JPanel labelPanel = new JPanel();
		labelPanel.setPreferredSize(new Dimension(250, 50));
		rightPanel.add(labelPanel);

		JLabel xValueLabel = new JLabel("X value");
		labelPanel.add(xValueLabel);

		JLabel yValueLabel = new JLabel("Y value");
		labelPanel.add(yValueLabel);

		JPanel inputPointsPanel = new JPanel();
		inputPointsPanel.setPreferredSize(new Dimension(350, 600));
		rightPanel.add(inputPointsPanel);

		xValueTextField = new JTextField();
		inputPointsPanel.add(xValueTextField);
		xValueTextField.setColumns(10);

		yValueTextField = new JTextField();
		inputPointsPanel.add(yValueTextField);
		yValueTextField.setColumns(10);
	}

	private JPanel createChartPanel() {
		String chartTitle = "Approximation by radial functions method";
		String xAxisLabel = "X";
		String yAxisLabel = "Y";

		dataset = createDataset();

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

		return new ChartPanel(chart);
	}

	private XYDataset createDataset() {

		XYSeriesCollection dataset = new XYSeriesCollection();

		// Drawing y=sin(x) graph
		expected = new XYSeries("y=3*x");
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
		actual = new XYSeries("Approximation points");

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
