package com.kluyuko.andrey.utils;

import java.util.List;

import org.jfree.data.xy.XYSeries;

public class GraphUtils {

	private GraphUtils() {
	}

	public static XYSeries createXYSeries(String title, List<Double> xSeries, List<Double> ySeries) {
		XYSeries series = new XYSeries(title);
		for (int i = 0; i < xSeries.size(); i++) {
			series.add(xSeries.get(i), ySeries.get(i));
		}
		return series;
	}
}
