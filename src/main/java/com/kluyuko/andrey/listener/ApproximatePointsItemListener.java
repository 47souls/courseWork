package com.kluyuko.andrey.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.kluyuko.andrey.utils.GUIUtils;

public class ApproximatePointsItemListener implements ItemListener {

	private JTextField xTextField;
	private JTextField yTextField;
	private JButton drawButton;
	
	private static final String DRAW = "Намалювати графік";
	private static final String APPROXIMATE = "Апроксимувати точку";

	public ApproximatePointsItemListener(JTextField xTextField, JTextField yTextField, JButton drawButton) {
		this.xTextField = xTextField;
		this.yTextField = yTextField;
		this.drawButton = drawButton;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox checkbox = (JCheckBox) e.getSource();
		
		GUIUtils.clearTextFields(xTextField);
		GUIUtils.clearTextFields(yTextField);
		if (checkbox.isSelected()) {
			drawButton.setText(APPROXIMATE);
			yTextField.setEnabled(false);
		} else {
			drawButton.setText(DRAW);
			yTextField.setEnabled(true);
		}
	}

}
