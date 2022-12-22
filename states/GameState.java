package com.game15.game.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.game15.game.Game;
import com.game15.game.board.Board;
import com.game15.game.board.Point;
import com.game15.game.inputs.Button;

public class GameState extends State {
	
	private Board board;
	private int boardSize = 4;
	private int tileSize = game.height()/boardSize;
	private boolean isSolved = true;
	
	private Button scrambler,restart, settings;

	public GameState(Game game) {
		super(game);
		board = new Board(boardSize,tileSize);
		
		init();
	}
	
	private void init() {
		int sizeX = 80, sizeY = 40;
		scrambler = new Button(game, "Scramble", game.width()-sizeX-40, game.height()/2-sizeY/2,sizeX,sizeY);
		scrambler.offSetX(-32);
		restart = new Button(game, "Restart", game.width()-sizeX-40, game.height()/2-sizeY/2,sizeX,sizeY);
		restart.offSetX(-25);
		settings = new Button(game,"Settings", game.width()-sizeX, 0, sizeX,sizeY);
		settings.offSetX(-sizeX/4-4);
	}

	@Override
	public void tick() {
		if (game.mouseManager().isLeftPressed()) {
			if (!isSolved && game.mouseX() < boardSize*tileSize) {
				Point mouse = new Point(game.mouseX(),game.mouseY());
				board.move(board.getTileFromPoint(mouse));
				game.pause(false);
				game.inspection(false);
				
				if (board.isSolved()) {
					isSolved = true;
					game.pause(true);
				}
			}
		}
		board.tick();
		
		buttonManager();
	}
	
	private void buttonManager() {
		if (!isSolved) {
			restart.tick();
			if (restart.isClicked()) {
				board.createBoard();
				isSolved = true;
				game.pause(true);
				game.inspection(false);
			}
			else if (restart.isHovered())
				restart.setColor(Color.orange, Color.black);
			else
				restart.setColor(null,Color.black);
		} else {
			scrambler.tick();
			if (scrambler.isClicked()) {
				board.scramble();
				isSolved = false;
				game.resetTime();
				game.inspection(true);
			}
			else if (scrambler.isHovered())
				scrambler.setColor(Color.orange, Color.black);
			else
				scrambler.setColor(null,Color.black);
			
			settings.tick();
			if (settings.isClicked()) {
				game.chgState();
				game.pause(true);
			}
			else if (settings.isHovered())
				settings.setColor(Color.orange, Color.black);
			else
				settings.setColor(null, Color.black);
		}
	}

	@Override
	public void render(Graphics g) {
		board.render(g);
		if (!isSolved)
			restart.render(g);
		else {
			scrambler.render(g);
			settings.render(g);
		}
		
		Font font = g.getFont();
		if (font.getSize() != board.fontSize()-5) {
			Font newFont = font.deriveFont(board.fontSize()-5F);
			g.setFont(newFont);
		}
		
		int x = (int) board.fontSize()*2;
		int y = game.height()/2+game.height()/8;
		int xOffset = 11;
		g.setColor(Color.black);
		if (board.moveCount()/10 < 1)
			g.drawString(""+board.moveCount(), game.width()-x*2+xOffset+7, y);
		else
			g.drawString(""+board.moveCount(), game.width()-x*2 +xOffset, y);
	}

}
