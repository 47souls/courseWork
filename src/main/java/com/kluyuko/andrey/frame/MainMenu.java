package com.kluyuko.andrey.frame;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 6050984112084584239L;
	
	public MainMenu() {
		super("Головне меню");
		setSize(400, 325);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JButton btnNewButton = new JButton("1 вимірний випадок");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FunctionApproximationFrame().setVisible(true);
			}
		});
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("2 вимірний випадок");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegionApproximationFrame().setVisible(true);
			}
		});
		getContentPane().add(btnNewButton_1);
	}

	
}
