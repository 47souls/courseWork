package com.kluyuko.andrey.utils;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Utils {

	private Utils() {
	}

	public static void addLabelsToPanel(JPanel jPanel, double xValue, double yValue) {
		jPanel.add(new JLabel("x = " + xValue + ", "));
		jPanel.add(new JLabel("y = " + yValue + "; "));

	}
}
