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

public class DrawGraph extends JFrame {

	public DrawGraph() {
		super("XY Line Chart Example with JFreechart");

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
		// Drawing simulating points
		XYSeries actual = new XYSeries("some points");
		for (double i = 1; i < 10.0; i += 1.0) {
			actual.add(i, Math.sin(i));
		}
		dataset.addSeries(expected);
		dataset.addSeries(actual);
		return dataset;
	}
}
