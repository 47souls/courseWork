package com.kluyuko.andrey.utils;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIUtils {

	private static final Color FAILURE_COLOUR = Color.PINK;
	private static final Color SUCCESS_COLOUR = Color.GREEN;
	private static final String INCORRECT_INPUT_TEXT = "It is not a number!";

	private GUIUtils() {
	}

	public static void addLabelToPanel(JTextArea textArea, double value, String type, boolean newLine) {
		if (newLine) {
			textArea.append(type + " = " + String.format("%.3f", value) + "; " + "\n");
		} else {
			textArea.append(type + " = " + String.format("%.3f", value) + "; ");
		}
	}

	public static void clearTextFields(JTextField inputField) {
		inputField.setText("");
		inputField.setBackground(Color.WHITE);
	}

	public static void showAlreadyAddedErrorDialog(JFrame frame, String which, double value) {
		JOptionPane.showMessageDialog(frame, "Значення " + which + " = " + value + " вже додано!",
				"Помилка вводу даних", JOptionPane.ERROR_MESSAGE);
	}

	public static void showIncorrectInputErrorDialog(JFrame frame, String which, String value) {
		JOptionPane.showMessageDialog(frame, "Значення " + which + " = " + value + " не є числом!",
				"Помилка вводу даних", JOptionPane.ERROR_MESSAGE);
	}

	public static void showIncorrectRangeDialog(JFrame frame, String from, String to) {
		JOptionPane.showMessageDialog(frame, "Значення " + from + " більше за  " + to, "Помилка вводу даних",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void showIncorrectNDialog(JFrame frame, String n) {
		JOptionPane.showMessageDialog(frame, "Значення " + n + " більше нуля!", "Помилка вводу даних",
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

	public static void addActualXY(JFrame self, JTextArea textArea, JTextField xValueTextField,
			JTextField yValueTextField, List<Double> xExpected, List<Double> yExpected) {
		double x = 0;
		double y = 0;
		boolean isXInputFielsValid = false;
		boolean isYInputFielsValid = false;

		x = validateThatIsDouble(xValueTextField);
		y = validateThatIsDouble(yValueTextField);

		// TODO maybe it should be refactored?????

		if (!Double.isNaN(x)) {
			if (!xExpected.contains(x)) {
				isXInputFielsValid = true;
				xExpected.add(x);
			} else {
				xValueTextField.setBackground(Color.PINK);
				showAlreadyAddedErrorDialog(self, "x", x);
			}
		} else {
			showIncorrectInputErrorDialog(self, "x", xValueTextField.getText());
			xValueTextField.setBackground(Color.PINK);
		}

		if (!Double.isNaN(y)) {
			if (!yExpected.contains(y)) {
				isYInputFielsValid = true;
				yExpected.add(y);
			} else {
				yValueTextField.setBackground(Color.PINK);
				showAlreadyAddedErrorDialog(self, "y", y);
			}
		} else {
			showIncorrectInputErrorDialog(self, "y", yValueTextField.getText());
			yValueTextField.setBackground(Color.PINK);
		}

		if (isXInputFielsValid && isYInputFielsValid) {
			addLabelToPanel(textArea, x, xExpected.size() + ") " + "x", false);
			addLabelToPanel(textArea, y, "y", true);
			clearTextFields(xValueTextField);
			clearTextFields(yValueTextField);
		}
	}

	public static void addApproximatedXY(double x, double y, JFrame self, JTextArea textArea,
			JTextField xValueTextField, JTextField yValueTextField, List<Double> xExpected, List<Double> xActual,
			List<Double> yActual) {

		boolean isXInputFielsValid = false;

		// TODO maybe it should be refactored?????

		if (!Double.isNaN(x)) {
			if (!xExpected.contains(x)) {
				isXInputFielsValid = true;
				xActual.add(x);
			} else {
				xValueTextField.setBackground(Color.PINK);
				showAlreadyAddedErrorDialog(self, "x", x);
			}
		} else {
			showIncorrectInputErrorDialog(self, "x", xValueTextField.getText());
		}

		if (isXInputFielsValid) {
			addLabelToPanel(textArea, x, xActual.size() + ") " + "x", false);
			addLabelToPanel(textArea, y, "y", true);
			clearTextFields(xValueTextField);
			clearTextFields(yValueTextField);
		}
	}

	public static void addGeneratedData(JFrame self, JTextArea textArea, JTextField fromTextField,
			JTextField toTextField, JTextField nOfDivisionsTextField, int selectedIndex, List<Double> xActual,
			List<Double> yActual) {
		double value;

		xActual.clear();
		yActual.clear();

		double from = validateThatIsDouble(fromTextField);
		double to = validateThatIsDouble(toTextField);
		double n = validateThatIsDouble(nOfDivisionsTextField);

		if (from < to) {
			if (n > 0) {
				for (double i = from; i <= to; i += (to - from) / n) {
					xActual.add(i);
					addLabelToPanel(textArea, i, xActual.size() + ") " + "x", false);

					switch (selectedIndex) {
					case 0:
						value = Math.sin(i);
						yActual.add(value);
						addLabelToPanel(textArea, value, "y", true);
						break;
					case 1:
						value = Math.cos(i);
						yActual.add(value);
						addLabelToPanel(textArea, value, "y", true);
						break;
					case 2:
						value = 1 / i;
						yActual.add(value);
						addLabelToPanel(textArea, value, "y", true);
						break;
					case 3:
						value = 1 / Math.pow(i, 2);
						yActual.add(value);
						addLabelToPanel(textArea, value, "y", true);
						break;
					}
				}
			} else {
				// show incorrect n dialog
				showIncorrectNDialog(self, Double.toString(n));
			}
		} else {
			showIncorrectRangeDialog(self, Double.toString(from), Double.toString(to));
		}

	}
}
