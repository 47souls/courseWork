package com.kluyuko.andrey.utils;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIUtils {

	private static final Color FAILURE_COLOUR = Color.PINK;
	private static final Color SUCCESS_COLOUR = Color.GREEN;
	private static final String INCORRECT_INPUT_TEXT = "It is not a number!";

	private GUIUtils() {
	}

	public static void addLabelToPanel(JPanel jPanel, double value, String type) {
		jPanel.add(new JLabel(type + " = " + value + "; "));
	}

	public static void notifySuccessful(JTextField inputField) {
		inputField.setText("");
		inputField.setBackground(Color.WHITE);
	}

	public static void showAlreadyAddedErrorDialog(JFrame frame, String which, double value) {
		JOptionPane.showMessageDialog(frame, "Value of " + which + " = " + value + " is already added!", "Input error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showIncorrectInputErrorDialog(JFrame frame, String which, String value) {
		JOptionPane.showMessageDialog(frame, "Value of " + which + " = " + value + " is not a number!", "Input error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static double validateThatIsDouble(JTextField textField) {
		String text = textField.getText();
		double value = Double.NaN;

		if (text != null && !text.isEmpty()) {
			try {
				value = Double.parseDouble(text);
				textField.setBackground(SUCCESS_COLOUR);
			} catch (NumberFormatException exception) {
				textField.setBackground(FAILURE_COLOUR);
				textField.setToolTipText(INCORRECT_INPUT_TEXT);
				return value;
			}
		}
		return value;
	}

	public static void addActualXY(JFrame self, JPanel panel, JTextField xValueTextField, JTextField yValueTextField,
			List<Double> xExpected, List<Double> yExpected) {
		double x = 0;
		double y = 0;
		boolean isXInputFielsValid = false;
		boolean isYInputFielsValid = false;

		x = GUIUtils.validateThatIsDouble(xValueTextField);
		y = GUIUtils.validateThatIsDouble(yValueTextField);

		// TODO maybe it should be refactored?????

		if (!Double.isNaN(x)) {
			if (!xExpected.contains(x)) {
				isXInputFielsValid = true;
				xExpected.add(x);
			} else {
				xValueTextField.setBackground(Color.PINK);
				GUIUtils.showAlreadyAddedErrorDialog(self, "x", x);
			}
		} else {
			GUIUtils.showIncorrectInputErrorDialog(self, "x", xValueTextField.getText());
		}

		if (!Double.isNaN(y)) {
			if (!yExpected.contains(y)) {
				isYInputFielsValid = true;
				yExpected.add(y);
			} else {
				yValueTextField.setBackground(Color.PINK);
				GUIUtils.showAlreadyAddedErrorDialog(self, "y", y);
			}
		} else {
			GUIUtils.showIncorrectInputErrorDialog(self, "y", yValueTextField.getText());
		}

		if (isXInputFielsValid && isYInputFielsValid) {
			GUIUtils.addLabelToPanel(panel, x, "x");
			GUIUtils.addLabelToPanel(panel, y, "y");
			GUIUtils.notifySuccessful(xValueTextField);
			GUIUtils.notifySuccessful(yValueTextField);
		}

		panel.validate();
		panel.repaint();
	}

	public static void addApproximatedXY() {
		// TODO Auto-generated method stub
		
	}
}
