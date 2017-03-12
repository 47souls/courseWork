package com.kluyuko.andrey.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PointsKeyListener extends KeyAdapter {

	private double xValue;
	private double yValue;

	public PointsKeyListener() {
	}

	public double getxValue() {
		return xValue;
	}

	public void setxValue(double xValue) {
		this.xValue = xValue;
	}

	public double getyValue() {
		return yValue;
	}

	public void setyValue(double yValue) {
		this.yValue = yValue;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

		}
	}
}
