package com.game15.game.display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
	private JFrame frame;
	private Canvas canvas;
	
	private static int width;
	private static int height;
	private static String title;
	
	public Display(String title, int width, int scale) {
		this.title = title;
		this.width = width*scale;
		height = this.width/4*3;
		
		init();
	}
	
	private void init() {
		frame = new JFrame(title);
		frame.setSize(width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	
	public Canvas canvas() {
		return canvas;
	}
	
	public JFrame frame() {
		return frame;
	}
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
}
