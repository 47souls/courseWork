package com.kluyuko.andrey.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.kluyuko.andrey.panel.CircleParamsPanel;
import com.kluyuko.andrey.panel.ElipseParamsPanel;
import com.kluyuko.andrey.panel.OtherParamsPanel;

public class TypeFigureActionListener implements ActionListener {

	private JPanel valuePanel;

	public TypeFigureActionListener(JPanel vPanel) {
		this.valuePanel = vPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
		int index = comboBox.getSelectedIndex();
		
		switch (index) {
			case 0:
				valuePanel.add(new JLabel("Ahahhaa"));
				break;
			case 1:
				valuePanel.add(new ElipseParamsPanel());
				break;
			case 2:
				valuePanel.add(new OtherParamsPanel());
				break;
		}
		
		valuePanel.repaint();
	}

}
