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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.kluyuko.andrey.calculator.Calculator;
import com.kluyuko.andrey.listener.InputFieldsFocusListener;
import com.kluyuko.andrey.listener.PointsKeyListener;
import com.kluyuko.andrey.utils.GUIUtils;
import com.kluyuko.andrey.utils.GraphUtils;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private List<Double> xExpected;
	private List<Double> yExpected;
	private List<Double> yActual;
	private List<Double> constants;
	private boolean isInputtingExpectedNumbers;
	private String chartTitle = "Approximation by radial functions method";
	private String xAxisLabel = "X";
	private String yAxisLabel = "Y";

	// points dataSet
	private ChartPanel chartPanel;

	private XYSeriesCollection dataset;
	private XYSeries actual;
	private XYSeries expected;

	private Calculator calculator;

	private JFrame self = this;
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

		// Creation of graphical part

		dataset = new XYSeriesCollection();
		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
		graphPanel = new ChartPanel(chart);
		graphPanel.setPreferredSize(new Dimension(350, 550));
		leftPanel.add(graphPanel);

		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setPreferredSize(new Dimension(350, 100));
		leftPanel.add(buttonsPanel);

		JButton drawButton = new JButton("Draw graph");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expected = GraphUtils.createXYSeries("Some undefined graph", xExpected, yExpected);
				dataset.addSeries(expected);
				JFreeChart newChart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
				chartPanel.setChart(newChart);
			}
		});
		buttonsPanel.add(drawButton);

		JButton removePointsButton = new JButton("Remove points");
		removePointsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataset.removeAllSeries();
				expected.clear();
			}
		});
		buttonsPanel.add(removePointsButton);

		JButton addPointsButton = new JButton("Add point");
		addPointsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double x = 0;
				double y = 0;
				boolean isInputFielsValid = false;

				x = GUIUtils.validateThatIsDouble(xValueTextField);
				y = GUIUtils.validateThatIsDouble(yValueTextField);

				// TODO maybe it should be refactored?????

				if (!Double.isNaN(x)) {
					if (!xExpected.contains(x)) {
						xExpected.add(x);
						GUIUtils.clearInputField(xValueTextField);
					} else {
						GUIUtils.showAlreadyAddedErrorDialog(self, "x", x);
					}
				} else {
					GUIUtils.showIncorrectInputErrorDialog(self, "x", xValueTextField.getText());
				}

				if (!Double.isNaN(y)) {
					if (!yExpected.contains(y)) {
						yExpected.add(y);
						GUIUtils.clearInputField(yValueTextField);
						isInputFielsValid = true;
					} else {
						GUIUtils.showAlreadyAddedErrorDialog(self, "y", y);
					}
				} else {
					GUIUtils.showIncorrectInputErrorDialog(self, "y", yValueTextField.getText());
				}

				if (isInputFielsValid) {
					GUIUtils.addLabelToPanel(xyValuePanel, x);
					GUIUtils.addLabelToPanel(xyValuePanel, y);
				}

				xyValuePanel.validate();
				xyValuePanel.repaint();
			}

		});
		buttonsPanel.add(addPointsButton);

		JButton clearPointArrayButton = new JButton("Clear point array");
		clearPointArrayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * TODO 1) Think about refactoring of this shit
				 */
				xExpected.clear();
				yExpected.clear();
				xyValuePanel.removeAll();
				xyValuePanel.repaint();
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

		xValueTextField.addFocusListener(new InputFieldsFocusListener());
		yValueTextField.addFocusListener(new InputFieldsFocusListener());

		xyValuePanel = new JPanel();
		xyValuePanel.setPreferredSize(new Dimension(350, 600));
		rightPanel.add(xyValuePanel);

	}

	private XYDataset createDataset() {

		XYSeriesCollection dataset = new XYSeriesCollection();

		generateExpectedDataset();

		calculator = new Calculator(xExpected, yExpected);
		constants = calculator.calculateConstants();

		// Drawing simulating points
		actual = new XYSeries("Approximation points");

		double approximatedPoint = 0.05;
		System.out.println("Approximated point : " + approximatedPoint + " and value: "
				+ calculator.approximate(constants, xExpected, approximatedPoint));

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
