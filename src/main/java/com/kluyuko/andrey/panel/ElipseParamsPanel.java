package com.kluyuko.andrey.panel;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ElipseParamsPanel extends JPanel {

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public ElipseParamsPanel(){
		setPreferredSize(new Dimension(350, 75));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(175, 75));
		add(panel);
		
		JLabel lblX = new JLabel("Радіус кола X");
		panel.add(lblX);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(7);
		
		JLabel lblNewLabel_1 = new JLabel("Радіус кола Y");
		panel.add(lblNewLabel_1);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(7);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		panel_1.setPreferredSize(new Dimension(175, 75));
		add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(75, 75));
		panel_1.add(panel_2);
		
		JLabel lblNewLabel = new JLabel("Центр");
		panel_2.add(lblNewLabel);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(100, 75));
		panel_1.add(panel_3);
		
		JLabel lblX_1 = new JLabel("X");
		panel_3.add(lblX_1);
		
		textField_1 = new JTextField();
		panel_3.add(textField_1);
		textField_1.setColumns(9);
		
		JLabel lblY = new JLabel("Y");
		panel_3.add(lblY);
		
		textField_2 = new JTextField();
		panel_3.add(textField_2);
		textField_2.setColumns(9);
	}

}
