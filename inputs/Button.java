package com.game15.game.inputs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;

import com.game15.game.Game;

public class Button {
	
	private int x,y,sizeX,sizeY,offSetX = 0;
	private float fontSize = 15;
	private String name;
	private Game game;
	private boolean hover = false, clicked;
	private Color backColor, wordColor = Color.black;
	
	public Button(Game game, String name, int x, int y, int sizeX, int sizeY) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.name = name;
	}
	
	public void tick() {
		if (game.mouseX() > x && game.mouseX() < x+sizeX && game.mouseY() > y && game.mouseY() < y+sizeY) {
			hover = true;
			game.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if (game.mouseManager().isLeftClicked()) {
				clicked = true;
				game.mouseManager().setLeftClicked(false);
			}
		} else {
			hover = false;
			game.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public void render(Graphics g) {
		Font font = g.getFont();
		if (font.getSize() != fontSize) {
			Font newFont = font.deriveFont(fontSize);
			g.setFont(newFont);
		}
		
		if (backColor != null) {
			g.setColor(backColor);
			g.fillRect(x+1,y+1,sizeX-2,sizeY-2);
		}
		g.setColor(Color.black);
		g.drawRect(x,y,sizeX,sizeY);
		g.setColor(wordColor);
		g.drawString(name, x+sizeX/2+offSetX, y+sizeY/2+ (int)(fontSize/2));
	}
	
	public void setColor(Color backColor, Color wordColor) {
		this.backColor = backColor;
		this.wordColor = wordColor;
	}
	
	public void setFontSize(float size) {
		fontSize = size;
	}
	
	public void offSetX(int x) {
		offSetX = x;
	}
	
	public boolean isHovered() {
		return hover;
	}
	
	public boolean isClicked() {
		if (clicked) {
			clicked = false;
			return true;
		}
		return false;
	}
}
