package com.game15.game.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Tile {
	private Point point;
	private Point maskPoint;
	private int size, value, boardX, boardY;
	private float fontSize = 20;
	private boolean empty;
	private Color color;
	
	public Tile(Point point, int size, int value, boolean empty, Color color) {
		this.point = point;
		maskPoint = point.copyPoint(point);
		this.size = size;
		this.value = value;
		this.empty = empty;
		this.color = color;
		boardX = point.intX()/size;
		boardY = point.intY()/size;
	}
	
	public void updatePoint(Point point) {
		this.point = point;
		boardX = point.intX()/size;
		boardY = point.intY()/size;
	}
	
	public void updateMask(Point point) {
		maskPoint = point.copyPoint(point);
	}
	
	public Point maskPoint() {
		return maskPoint;
	}
	
	public boolean moveMask(float speed) {
		if (!maskPoint.isEquals(point) && !maskPoint.approximates(point, speed)) {
				maskPoint.moveTowards(point, speed);
				return true;
		}
		return false;
	}
	
	public Point point() {
		return point;
	}

	public int boardX() {
		return boardX;
	}

	public void boardX(int boardX) {
		this.boardX = boardX;
	}

	public int boardY() {
		return boardY;
	}

	public void boardY(int boardY) {
		this.boardY = boardY;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public int value() {
		return value;
	}

	public void fontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void render(Graphics g) {
		Font font = g.getFont();
		if (font.getSize() != fontSize) {
			Font newFont = font.deriveFont(fontSize);
			g.setFont(newFont);
		}
		
		g.setColor(color);
		g.fillRect(maskPoint.intX(), maskPoint.intY(), size-1,size-1);
		g.setColor(Color.white);
		g.drawString(""+value,maskPoint.intX()+size/2- 5,maskPoint.intY()+size/2+ (int)(fontSize/2));
	}

	
}
