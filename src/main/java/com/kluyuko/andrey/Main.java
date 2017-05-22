package com.kluyuko.andrey;

import javax.swing.SwingUtilities;

import com.kluyuko.andrey.frame.MainMenu;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainMenu().setVisible(true);
			}
		});
	}

}
