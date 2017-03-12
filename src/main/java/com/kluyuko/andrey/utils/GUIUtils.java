package com.kluyuko.andrey.utils;

import java.awt.Color;

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

	public static void addLabelToPanel(JPanel jPanel, double value) {
		jPanel.add(new JLabel("x = " + value + ", "));
	}

	public static void clearInputField(JTextField inputField) {
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
}
