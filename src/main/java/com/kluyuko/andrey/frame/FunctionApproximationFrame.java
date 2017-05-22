package com.kluyuko.andrey.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

@SuppressWarnings("serial")
public class FunctionApproximationFrame extends JFrame {

	private List<Double> xExpected;
	private List<Double> yExpected;
	private List<Double> xActual;
	private List<Double> yActual;
	private String chartTitle = "Апроксимація функцій методом радіальних-базисних функцій";
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
	private JPanel availableFunctionPanel;
	private JPanel typeOfFunctPanel;
	private JPanel typeOfParamPanel;
	private JPanel textFunctPanel;
	private JPanel chooseFunctPanel;
	private JLabel lblNewLabel;
	private JComboBox<String> functionComboBox;
	private JLabel paramLabel;
	private JComboBox<String> eComboBox;
	private JCheckBox approximationCheckBox;
	private JPanel actualPointsPanel;
	private JPanel approximatedPointsPanel;
	private JPanel infoPanel;
	private JPanel choosePanel;
	private JComboBox<String> functionGeneratedComboBox;
	private JTextField fromTextField;
	private JTextField toTextField;
	private JLabel fromTextLabel;
	private JLabel toTextLabel;
	private JLabel nOfDivisionsTextLabel;
	private JTextField nOfDivisionsTextField;
	private JTextArea actualArea;
	private JTextArea approximatedArea;
	private JScrollPane approximatedScrollPane;
	private JScrollPane actualScrollPane;
	private JPanel pointsInfoPanel;
	private JPanel pointsInfoPanel2;
	private JPanel infoTextPanel;
	private JPanel infoTextPanel2;
	private JLabel lblSdf;
	private JLabel lblNewLabel_1;
	private JPanel panel;
	private JPanel oversightPanel;
	private JButton fillDataButton;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel oversight1Label;
	private JTextField oversight1ValueTextField;
	private JLabel oversight2Label;
	private JTextField oversight2ValueTextField;
	private JLabel lbll;
	private JTextField oversight1PercentageTextField;
	private JLabel label;
	private JTextField oversight2PercentageTextField;

	public FunctionApproximationFrame() {
		super("Дипломна робота");
		xExpected = new ArrayList<>();
		yExpected = new ArrayList<>();
		xActual = new ArrayList<>();
		yActual = new ArrayList<>();

		setSize(900, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
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
					// Approximation and painting actual point from x input
					// logic
					if (dataset.getSeries().contains(actual)) {
						dataset.removeSeries(actual);
					} else {
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
					GUIUtils.addActualXY(self, actualArea, xValueTextField, yValueTextField, xExpected, yExpected);
				} else {

					int typeIndex = functionComboBox.getSelectedIndex();
					double eps = Double.parseDouble(eComboBox.getSelectedItem().toString());

					calculator = Calculator.createCalculator(xExpected, yExpected, typeIndex, eps);
					calculator.calculateConstants();

					double x = GUIUtils.validateThatIsDouble(xValueTextField);
					double y = calculator.approximate(x);

					yActual.add(y);

					// Adding point to approximation panel and graph
					GUIUtils.addApproximatedXY(x, y, self, approximatedArea, xValueTextField, yValueTextField,
							xExpected, xActual, yActual);
				}

			}

		});
		buttonsPanel.add(addPointsButton);

		JButton clearPointArrayButton = new JButton("Очистити масив точок");
		clearPointArrayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!approximationCheckBox.isSelected()) {
					xExpected.clear();
					yExpected.clear();
					actualArea.setText("");
				} else {
					xActual.clear();
					yActual.clear();
					approximatedArea.setText("");
				}
			}
		});
		buttonsPanel.add(clearPointArrayButton);

		rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setPreferredSize(new Dimension(250, 650));
		getContentPane().add(rightPanel);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		labelPanel = new JPanel();
		labelPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		labelPanel.setPreferredSize(new Dimension(250, 25));
		rightPanel.add(labelPanel);

		xValueLabel = new JLabel("Значення X");
		labelPanel.add(xValueLabel);

		yValueLabel = new JLabel("Значення Y");
		labelPanel.add(yValueLabel);

		inputPointsPanel = new JPanel();
		inputPointsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		inputPointsPanel.setPreferredSize(new Dimension(350, 55));
		rightPanel.add(inputPointsPanel);

		xValueTextField = new JTextField();
		inputPointsPanel.add(xValueTextField);
		xValueTextField.setColumns(10);

		yValueTextField = new JTextField();
		inputPointsPanel.add(yValueTextField);
		yValueTextField.setColumns(10);

		approximationCheckBox = new JCheckBox("Режим апрокcимації");
		approximationCheckBox
				.addItemListener(new ApproximatePointsItemListener(xValueTextField, yValueTextField, drawButton));

		fillDataButton = new JButton("Згенерувати дані");
		fillDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Generating data using declared functions
				GUIUtils.addGeneratedData(self, actualArea, fromTextField, toTextField, nOfDivisionsTextField,
						functionGeneratedComboBox.getSelectedIndex(), xExpected, yExpected);
			}
		});
		buttonsPanel.add(fillDataButton);
		inputPointsPanel.add(approximationCheckBox);

		xValueTextField.addFocusListener(new InputFieldsFocusListener());
		yValueTextField.addFocusListener(new InputFieldsFocusListener());

		xyValuePanel = new JPanel();
		xyValuePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		xyValuePanel.setLayout(new BoxLayout(xyValuePanel, BoxLayout.X_AXIS));
		xyValuePanel.setPreferredSize(new Dimension(350, 200));
		rightPanel.add(xyValuePanel);

		actualPointsPanel = new JPanel();
		actualPointsPanel.setLayout(new BoxLayout(actualPointsPanel, BoxLayout.Y_AXIS));
		actualPointsPanel.setPreferredSize(new Dimension(175, 225));
		xyValuePanel.add(actualPointsPanel);

		approximatedPointsPanel = new JPanel();
		approximatedPointsPanel.setLayout(new BoxLayout(approximatedPointsPanel, BoxLayout.Y_AXIS));
		approximatedPointsPanel.setPreferredSize(new Dimension(175, 225));
		xyValuePanel.add(approximatedPointsPanel);

		pointsInfoPanel = new JPanel();
		pointsInfoPanel.setPreferredSize(new Dimension(120, 150));
		actualPointsPanel.add(pointsInfoPanel);

		actualArea = new JTextArea();
		actualArea.setEditable(false);
		actualArea.setPreferredSize(new Dimension(120, 150));
		actualScrollPane = new JScrollPane(actualArea);
		pointsInfoPanel.add(actualScrollPane);
		actualScrollPane.setPreferredSize(new Dimension(120, 150));

		infoTextPanel = new JPanel();
		actualPointsPanel.add(infoTextPanel);

		lblSdf = new JLabel("Очікувані");
		infoTextPanel.add(lblSdf);

		pointsInfoPanel2 = new JPanel();
		pointsInfoPanel2.setPreferredSize(new Dimension(120, 150));
		approximatedPointsPanel.add(pointsInfoPanel2);

		approximatedArea = new JTextArea();
		approximatedArea.setEditable(false);
		approximatedArea.setPreferredSize(new Dimension(120, 150));
		approximatedScrollPane = new JScrollPane(approximatedArea);
		pointsInfoPanel2.add(approximatedScrollPane);
		approximatedScrollPane.setPreferredSize(new Dimension(120, 150));

		infoTextPanel2 = new JPanel();
		approximatedPointsPanel.add(infoTextPanel2);

		lblNewLabel_1 = new JLabel("Актуальні");
		infoTextPanel2.add(lblNewLabel_1);

		additionalInfoPanel = new JPanel();
		additionalInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		additionalInfoPanel.setPreferredSize(new Dimension(350, 325));
		additionalInfoPanel.setLayout(new BoxLayout(additionalInfoPanel, BoxLayout.Y_AXIS));
		rightPanel.add(additionalInfoPanel);

		functInfoPanel = new JPanel();
		functInfoPanel.setLayout(new BoxLayout(functInfoPanel, BoxLayout.Y_AXIS));
		functInfoPanel.setPreferredSize(new Dimension(350, 55));
		additionalInfoPanel.add(functInfoPanel);

		typeOfFunctPanel = new JPanel();
		typeOfFunctPanel.setLayout(new BoxLayout(typeOfFunctPanel, BoxLayout.Y_AXIS));
		typeOfFunctPanel.setPreferredSize(new Dimension(350, 25));
		functInfoPanel.add(typeOfFunctPanel);

		textFunctPanel = new JPanel();
		textFunctPanel.setPreferredSize(new Dimension(350, 7));
		typeOfFunctPanel.add(textFunctPanel);

		lblNewLabel = new JLabel("Тип апроксимаційної функції");
		textFunctPanel.add(lblNewLabel);

		chooseFunctPanel = new JPanel();
		chooseFunctPanel.setPreferredSize(new Dimension(350, 17));
		typeOfFunctPanel.add(chooseFunctPanel);

		functionComboBox = new JComboBox<>();
		functionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Мультиквадратична РБФ (МкРБФ)",
				"Обернена МкРБФ", "Обернена квадратична РБФ", "Гаусівська РБФ" }));
		chooseFunctPanel.add(functionComboBox);

		typeOfParamPanel = new JPanel();
		typeOfParamPanel.setPreferredSize(new Dimension(350, 18));
		functInfoPanel.add(typeOfParamPanel);

		paramLabel = new JLabel("Параметр форми: E =");
		typeOfParamPanel.add(paramLabel);

		eComboBox = new JComboBox<String>();
		eComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "0.5", "1.0", "5.0" }));
		typeOfParamPanel.add(eComboBox);

		availableFunctionPanel = new JPanel();
		availableFunctionPanel.setLayout(new BoxLayout(availableFunctionPanel, BoxLayout.Y_AXIS));
		availableFunctionPanel.setPreferredSize(new Dimension(350, 175));
		additionalInfoPanel.add(availableFunctionPanel);

		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(350, 25));
		availableFunctionPanel.add(infoPanel);

		fromTextLabel = new JLabel("Від");
		infoPanel.add(fromTextLabel);

		fromTextField = new JTextField();
		infoPanel.add(fromTextField);
		fromTextField.setColumns(8);

		toTextLabel = new JLabel("до");
		infoPanel.add(toTextLabel);

		toTextField = new JTextField();
		infoPanel.add(toTextField);
		toTextField.setColumns(8);

		nOfDivisionsTextLabel = new JLabel("Кількість поділів");
		infoPanel.add(nOfDivisionsTextLabel);

		nOfDivisionsTextField = new JTextField();
		infoPanel.add(nOfDivisionsTextField);
		nOfDivisionsTextField.setColumns(10);

		choosePanel = new JPanel();
		choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.Y_AXIS));
		choosePanel.setPreferredSize(new Dimension(350, 10));
		availableFunctionPanel.add(choosePanel);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(350, 10));
		choosePanel.add(panel);

		functionGeneratedComboBox = new JComboBox<String>();
		panel.add(functionGeneratedComboBox);
		functionGeneratedComboBox
				.setModel(new DefaultComboBoxModel<String>(new String[] { "sin(x)", "cos(x)", "1/x", "1/x^2" }));

		oversightPanel = new JPanel();
		oversightPanel.setLayout(new BoxLayout(oversightPanel, BoxLayout.Y_AXIS));
		availableFunctionPanel.add(oversightPanel);
		oversightPanel.setPreferredSize(new Dimension(350, 28));

		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(350, 7));
		oversightPanel.add(panel_1);

		oversight1Label = new JLabel("Похибка обчислень для значення\r\n");
		panel_1.add(oversight1Label);

		panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(350, 7));
		oversightPanel.add(panel_2);

		oversight1ValueTextField = new JTextField();
		panel_2.add(oversight1ValueTextField);
		oversight1ValueTextField.setColumns(10);
		
		lbll = new JLabel("=");
		panel_2.add(lbll);
		
		oversight1PercentageTextField = new JTextField();
		panel_2.add(oversight1PercentageTextField);
		oversight1PercentageTextField.setColumns(10);

		panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(350, 7));
		oversightPanel.add(panel_3);

		oversight2Label = new JLabel("Якась інша похибка обчислень");
		panel_3.add(oversight2Label);

		panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(350, 7));
		oversightPanel.add(panel_4);

		oversight2ValueTextField = new JTextField();
		panel_4.add(oversight2ValueTextField);
		oversight2ValueTextField.setColumns(10);
		
		label = new JLabel("=");
		panel_4.add(label);
		
		oversight2PercentageTextField = new JTextField();
		panel_4.add(oversight2PercentageTextField);
		oversight2PercentageTextField.setColumns(10);

	}

}
