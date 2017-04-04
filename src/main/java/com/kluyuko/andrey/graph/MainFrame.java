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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.kluyuko.andrey.calculator.Calculator;
import com.kluyuko.andrey.listener.ApproximatePointsItemListener;
import com.kluyuko.andrey.listener.InputFieldsFocusListener;
import com.kluyuko.andrey.utils.GUIUtils;
import com.kluyuko.andrey.utils.GraphUtils;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private List<Double> xExpected;
	private List<Double> yExpected;
	private List<Double> xActual;
	private List<Double> yActual;
	private List<Double> constants;
	private boolean isInputtingExpectedNumbers;
	private String chartTitle = "Апроксимація методом радіально-базисних функцій";
	private String xAxisLabel = "X";
	private String yAxisLabel = "Y";

	// points dataSet
	private ChartPanel chartPanel;

	private XYSeriesCollection dataset;
	private XYSeries expected;
	private XYSeries actual;

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
	private JPanel additionalInfoPanel;
	private JPanel functInfoPanel;
	private JPanel oversightInfoPanel;
	private JPanel typeOfFunctPanel;
	private JPanel typeOfParamPanel;
	private JPanel textFunctPanel;
	private JPanel chooseFunctPanel;
	private JLabel lblNewLabel;
	private JComboBox functionComboBox;
	private JLabel paramLabel;
	private JComboBox eComboBox;
	private JCheckBox approximationCheckBox;
	private JPanel actualPointsPanel;
	private JPanel approximatedPointsPanel;

	public MainFrame() {
		super("Дипломна робота");
		xExpected = new ArrayList<>();
		yExpected = new ArrayList<>();
		xActual = new ArrayList<>();
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
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(350, 550));
		leftPanel.add(chartPanel);

		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setPreferredSize(new Dimension(350, 100));
		leftPanel.add(buttonsPanel);

		final JButton drawButton = new JButton("Намалювати графік");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!approximationCheckBox.isSelected()) {
					drawButton.setText("Намалювати графік");
					// Just painting graph from user`s x and y input
					if (dataset.getSeries().contains(expected)) {
						// Releasing dataset
						dataset.removeSeries(expected);
					} else {
						// Drawing graph
						expected = GraphUtils.createXYSeries("Невизначений графік", xExpected, yExpected);
						dataset.addSeries(expected);
					}
				} else {
					drawButton.setText("Апроксимувати точки");
					// Approximation and painting actual point from x input
					// logic
					if (dataset.getSeries().contains(actual)) {
						dataset.removeSeries(actual);
					} else {
						int typeIndex = functionComboBox.getSelectedIndex();
						double eps = Double.parseDouble(eComboBox.getSelectedItem().toString());
						Calculator calculator = new Calculator(xExpected, yExpected, typeIndex, eps);
						constants = calculator.calculateConstants();

						double x = GUIUtils.validateThatIsDouble(xValueTextField);
						double y = calculator.approximate(constants, xExpected, x);
						System.out.println("y = " + y);

						xActual.add(x);
						yActual.add(y);

						actual = GraphUtils.createXYSeries("Aпроксимований графік", xActual, yActual);
						dataset.addSeries(actual);
					}

				}
			}
		});
		buttonsPanel.add(drawButton);

		JButton addPointsButton = new JButton("Додати точку");
		addPointsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!approximationCheckBox.isSelected()) {
					// Adding point to actual panel and graph
					GUIUtils.addActualXY(self, actualPointsPanel, xValueTextField, yValueTextField, xExpected, yExpected);
				} else {
					// Adding point to approximation panel and graph
					GUIUtils.addApproximatedXY();
				}

			}

			
		});
		buttonsPanel.add(addPointsButton);

		JButton clearPointArrayButton = new JButton("Очистити масив точок");
		clearPointArrayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * TODO 1) Think about refactoring of this shit
				 */
				xExpected.clear();
				yExpected.clear();
				actualPointsPanel.removeAll();
				actualPointsPanel.repaint();
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

		xValueLabel = new JLabel("Значення X");
		labelPanel.add(xValueLabel);

		yValueLabel = new JLabel("Значення Y");
		labelPanel.add(yValueLabel);

		inputPointsPanel = new JPanel();
		inputPointsPanel.setPreferredSize(new Dimension(350, 75));
		rightPanel.add(inputPointsPanel);

		xValueTextField = new JTextField();
		inputPointsPanel.add(xValueTextField);
		xValueTextField.setColumns(10);

		yValueTextField = new JTextField();
		inputPointsPanel.add(yValueTextField);
		yValueTextField.setColumns(10);

		approximationCheckBox = new JCheckBox("Режим апроксимації");
		approximationCheckBox.addItemListener(new ApproximatePointsItemListener(yValueTextField));
		inputPointsPanel.add(approximationCheckBox);

		xValueTextField.addFocusListener(new InputFieldsFocusListener());
		yValueTextField.addFocusListener(new InputFieldsFocusListener());

		xyValuePanel = new JPanel();
		xyValuePanel.setLayout(new BoxLayout(xyValuePanel, BoxLayout.X_AXIS));
		xyValuePanel.setPreferredSize(new Dimension(350, 225));
		rightPanel.add(xyValuePanel);

		actualPointsPanel = new JPanel();
		actualPointsPanel.setPreferredSize(new Dimension(175, 225));
		xyValuePanel.add(actualPointsPanel);

		approximatedPointsPanel = new JPanel();
		approximatedPointsPanel.setPreferredSize(new Dimension(175, 225));
		xyValuePanel.add(approximatedPointsPanel);

		additionalInfoPanel = new JPanel();
		additionalInfoPanel.setPreferredSize(new Dimension(350, 350));
		additionalInfoPanel.setLayout(new BoxLayout(additionalInfoPanel, BoxLayout.Y_AXIS));
		rightPanel.add(additionalInfoPanel);

		functInfoPanel = new JPanel();
		functInfoPanel.setLayout(new BoxLayout(functInfoPanel, BoxLayout.Y_AXIS));
		functInfoPanel.setPreferredSize(new Dimension(350, 175));
		additionalInfoPanel.add(functInfoPanel);

		typeOfFunctPanel = new JPanel();
		typeOfFunctPanel.setLayout(new BoxLayout(typeOfFunctPanel, BoxLayout.Y_AXIS));
		typeOfFunctPanel.setPreferredSize(new Dimension(350, 100));
		functInfoPanel.add(typeOfFunctPanel);

		textFunctPanel = new JPanel();
		typeOfFunctPanel.add(textFunctPanel);

		lblNewLabel = new JLabel("Тип апроксимаційної функції");
		textFunctPanel.add(lblNewLabel);

		chooseFunctPanel = new JPanel();
		typeOfFunctPanel.add(chooseFunctPanel);

		functionComboBox = new JComboBox();
		functionComboBox.setModel(new DefaultComboBoxModel(new String[] { "Мультиквадратична РБФ (МкРБФ)",
				"Обернена МкРБФ", "Обернена квадратична РБФ", "Гаусівська РБФ" }));
		chooseFunctPanel.add(functionComboBox);

		typeOfParamPanel = new JPanel();
		typeOfParamPanel.setPreferredSize(new Dimension(350, 75));
		functInfoPanel.add(typeOfParamPanel);

		paramLabel = new JLabel("Параметр форми: E =");
		typeOfParamPanel.add(paramLabel);

		eComboBox = new JComboBox();
		eComboBox.setModel(new DefaultComboBoxModel(new String[] { "0.5", "1.0", "5.0" }));
		typeOfParamPanel.add(eComboBox);

		oversightInfoPanel = new JPanel();
		oversightInfoPanel.setPreferredSize(new Dimension(350, 175));
		additionalInfoPanel.add(oversightInfoPanel);

	}

}
