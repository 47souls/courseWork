package com.kluyuko.andrey.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

	private List<Double> xExpected;
	private List<Double> yExpected;
	private List<Double> yActual;
	private List<Double> constants;

	// points dataSet
	private ChartPanel chartPanel;
	private XYDataset dataset;
	private XYSeries actual;
	private XYSeries expected;

	private Calculator calculator;

	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel graphPanel;
	private JPanel buttonsPanel;
	private JPanel labelPanel;
	private JPanel xyValuePanel;
	private JPanel inputPointsPanel;

	private JLabel xValueLabel;
	private JLabel yValueLabel;
	
	private JTextField xValueTextField;
	private JTextField yValueTextField;

	public MainFrame() {
		super("Year project");
		xExpected = new ArrayList<>();
		yExpected = new ArrayList<>();
		yActual = new ArrayList<>();
		constants = new ArrayList<>();

		setSize(900, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(650, 650));
		getContentPane().add(leftPanel);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		graphPanel = createChartPanel();
		graphPanel.setPreferredSize(new Dimension(350, 550));
		leftPanel.add(graphPanel);

		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setPreferredSize(new Dimension(350, 100));
		leftPanel.add(buttonsPanel);

		JButton drawButton = new JButton("Draw graph");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFreeChart chart = generateJFreeChart();
				chartPanel.setChart(chart);
			}
		});
		buttonsPanel.add(drawButton);

		JButton removePointsButton = new JButton("Remove points");
		removePointsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expected.clear();
			}
		});
		buttonsPanel.add(removePointsButton);

		JButton addPointsButton = new JButton("Add point");
		addPointsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO read value of inputed x and y and set it in arrayLists
				xyValuePanel.add(new JLabel("Bla"));
				xyValuePanel.validate();
				xyValuePanel.repaint();
			}
		});
		buttonsPanel.add(addPointsButton);
		
		JButton clearPointArrayButton = new JButton("Clear point array");
		clearPointArrayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO remove points from memory and clear interface
			}
		});
		buttonsPanel.add(clearPointArrayButton);

		rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setPreferredSize(new Dimension(250, 650));
		getContentPane().add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		labelPanel = new JPanel();
		labelPanel.setPreferredSize(new Dimension(250, 50));
		rightPanel.add(labelPanel);

		xValueLabel = new JLabel("X value");
		labelPanel.add(xValueLabel);

		yValueLabel = new JLabel("Y value");
		labelPanel.add(yValueLabel);

		inputPointsPanel = new JPanel();
		inputPointsPanel.setPreferredSize(new Dimension(350, 50));
		rightPanel.add(inputPointsPanel);

		xValueTextField = new JTextField();
		inputPointsPanel.add(xValueTextField);
		xValueTextField.setColumns(10);

		yValueTextField = new JTextField();
		inputPointsPanel.add(yValueTextField);
		yValueTextField.setColumns(10);

		xyValuePanel = new JPanel();
		xyValuePanel.setPreferredSize(new Dimension(350, 600));
		rightPanel.add(xyValuePanel);
	}

	private JPanel createChartPanel() {
		JFreeChart chart = generateJFreeChart();
		chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	private JFreeChart generateJFreeChart() {
		String chartTitle = "Approximation by radial functions method";
		String xAxisLabel = "X";
		String yAxisLabel = "Y";

		dataset = createDataset();

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
		return chart;
	}

	private XYDataset createDataset() {

		XYSeriesCollection dataset = new XYSeriesCollection();

		generateExpectedDataset();


		// Calculating constants
		/* TODO after this 2 lines xExpected is transforming into constants
		*	that's why calculator.calculateConstants()
		*
		*/
		calculator = new Calculator(xExpected, yExpected);
		constants = calculator.calculateConstants();

		// Drawing simulating points
		actual = new XYSeries("Approximation points");

		double approximatedPoint = 0.05;
		System.out.println("Approximated point : " + approximatedPoint + " and value: " + calculator.approximate(constants, xExpected, approximatedPoint));

		dataset.addSeries(expected);
		dataset.addSeries(actual);

		return dataset;
	}

	private void generateExpectedDataset() {
		
		// TODO it should be generated dynamically depending on user input!
		expected = new XYSeries("y = 3*x");
		for (int i = 0; i < 4; i++) {
			double x = i * 0.1;
			double y = 3 * x;
			expected.add(x, y);
			xExpected.add(i, x);
			yExpected.add(i, y);
		}
	}
}
