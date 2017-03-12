package com.kluyuko.andrey.listener;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class InputFieldsFocusListener implements FocusListener {

	private static final Color NORMAL_COLOR = Color.WHITE;
	private static final Color FAILURE_COLOUR = Color.PINK;
	private static final Color SUCCESS_COLOUR = Color.GREEN;
	private static final Color WARNING_COLOUR = Color.YELLOW;
	private static final String WARNING_TEXT = "Please input your number!";
	private static final String INCORRECT_INPUT_TEXT = "It is not a number!";
	private static final String EMPTY_INPUT_TEXT = "It is empty!";

	public InputFieldsFocusListener() {
	}

	@Override
	public void focusGained(FocusEvent e) {
		JTextField textField = (JTextField) e.getSource();
		textField.setBackground(NORMAL_COLOR);
		textField.setToolTipText(WARNING_TEXT);
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField textField = (JTextField) e.getSource();
		String text = textField.getText();
		if (text != null && !text.isEmpty()) {
			try {
				Double.parseDouble(text);
				textField.setBackground(SUCCESS_COLOUR);
			} catch (NumberFormatException exception) {
				textField.setBackground(FAILURE_COLOUR);
				textField.setToolTipText(INCORRECT_INPUT_TEXT);
			}
		} else {
			textField.setBackground(WARNING_COLOUR);
			textField.setToolTipText(EMPTY_INPUT_TEXT);
		}
	}

}
