package com.kluyuko.andrey.listener;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class ApproximatePointsItemListener implements ItemListener {

	private JTextField yTextField;

	public ApproximatePointsItemListener(JTextField yTextField) {
		this.yTextField = yTextField;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox checkbox = (JCheckBox) e.getSource();
		if (checkbox.isSelected()) {
			yTextField.setEnabled(false);
			yTextField.setBackground(Color.WHITE);
		} else {
			yTextField.setEnabled(true);
		}
	}

}
