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
import com.kluyuko.andrey.listener.TypeFigureActionListener;
import com.kluyuko.andrey.utils.GUIUtils;
import com.kluyuko.andrey.utils.GraphUtils;

@SuppressWarnings("serial")
public class RegionApproximationFrame extends JFrame {

	private List<Double> xExpected;
	private List<Double> yExpected;
	private List<Double> xActual;
	private List<Double> yActual;
	private String chartTitle = "Апроксимація областей методом радіальних-базисних функцій";
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
	private JPanel typeOfFunctPanel;
	private JPanel textFunctPanel;
	private JPanel chooseFunctPanel;
	private JLabel lblNewLabel;
	private JComboBox<String> functionComboBox;
	private JCheckBox approximationCheckBox;
	private JPanel actualPointsPanel;
	private JPanel approximatedPointsPanel;
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
	private JButton fillDataButton;
	private JPanel typeOfRegionPanel;
	private JPanel oversightPanel;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	private JTextField textField;
	private JLabel lblNewLabel_2;
	private JTextField textField_1;

	public RegionApproximationFrame() {
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
					// Removing region from graph it is already defined
					if (dataset.getSeries().contains(expected)) {
						// Releasing dataset
						dataset.removeSeries(expected);
					} else {
						// Drawing region
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
				/*if (!approximationCheckBox.isSelected()) {
					// Adding point to actual panel and graph
					if (!generationCheckBox.isSelected()) {
						GUIUtils.addActualRegion(self, actualArea, xValueTextField, yValueTextField, xExpected, yExpected);
					} else {
						int index = functionComboBox.getSelectedIndex();
						GUIUtils.addGeneratedRegion(self, actualArea, index, xValueTextField, yValueTextField, xExpected, yExpected);
					}
				} else {

					int typeIndex = functionComboBox.getSelectedIndex();
					//double eps = Double.parseDouble(eComboBox.getSelectedItem().toString());

					
				} */
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
		typeOfFunctPanel.setPreferredSize(new Dimension(350, 55));
		functInfoPanel.add(typeOfFunctPanel);

		textFunctPanel = new JPanel();
		textFunctPanel.setPreferredSize(new Dimension(350, 7));
		typeOfFunctPanel.add(textFunctPanel);

		lblNewLabel = new JLabel("Тип області");
		textFunctPanel.add(lblNewLabel);

		chooseFunctPanel = new JPanel();
		chooseFunctPanel.setPreferredSize(new Dimension(350, 17));
		typeOfFunctPanel.add(chooseFunctPanel);

		functionComboBox = new JComboBox<>();
		functionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Коло", "Трикутник", "Еліпс"}));
		chooseFunctPanel.add(functionComboBox);
		
		typeOfRegionPanel = new JPanel();
		functionComboBox.addActionListener(new TypeFigureActionListener(typeOfRegionPanel));
		typeOfRegionPanel.setPreferredSize(new Dimension(350, 150));
		additionalInfoPanel.add(typeOfRegionPanel);
		
		oversightPanel = new JPanel();
		oversightPanel.setPreferredSize(new Dimension(350, 170));
		oversightPanel.setLayout(new BoxLayout(oversightPanel, BoxLayout.Y_AXIS));
		additionalInfoPanel.add(oversightPanel);
		
		panel = new JPanel();
		oversightPanel.add(panel);
		
		label = new JLabel("Якась там помилка");
		panel.add(label);
		
		panel_1 = new JPanel();
		oversightPanel.add(panel_1);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_2 = new JLabel("=");
		panel_1.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		panel_1.add(textField_1);
		textField_1.setColumns(10);

	}

}
