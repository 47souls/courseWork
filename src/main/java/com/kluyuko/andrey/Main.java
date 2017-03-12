package com.kluyuko.andrey;

import javax.swing.SwingUtilities;

import com.kluyuko.andrey.graph.MainFrame;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

}
