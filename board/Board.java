package com.game15.game.board;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Board {
	private Tile[][] board;
	private Tile empty;
	private ArrayList<Tile> movingTiles;
	private int tileSize, boardSize;
	private float fontSize;
	private int moveCount = 0;
	
	public Board(int boardSize, int tileSize) {
		board = new Tile[boardSize][boardSize];
		movingTiles = new ArrayList<Tile>();
		
		this.tileSize = tileSize;
		this.boardSize = boardSize;
		fontSize = tileSize/4.5F;
		
		createBoard();
	}
	
	public void createBoard() {
		for (int y=0, c=1; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {
//				Color color;
//				if (y == 0 && x == 0)
//					color = Color.gray;
//				else if (y == 0 || x == 0)
//					color = Color.darkGray;
//				else if (y == 1 || x == 1)
//					color = Color.red;
//				else
//					color = Color.blue;
				Color color = Color.darkGray;
				
				if (y == boardSize-1 && x == boardSize-1) {
					board[y][x] = new Tile(new Point(x*tileSize,y*tileSize),tileSize,c,true,color);
					empty = board[y][x];
				}
				else
					board[y][x] = new Tile(new Point(x*tileSize,y*tileSize),tileSize,c,false,color);
				c++;
				board[y][x].fontSize(fontSize);
			}
		}
	}
	
	public void scramble() {
		moveCount = 0;
		int swapX = 0, swapY = 0;
		
		for (int y=0; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {
				swapX = (int) (Math.random()*boardSize);
				swapY = (int) (Math.random()*boardSize);
				while (x == swapX && y == swapY) {
					swapX = (int) (Math.random()*boardSize);
					swapY = (int) (Math.random()*boardSize);
				}
				swap(board[y][x],board[swapY][swapX],true);
			}
		}
				
		while ((boardSize-1 - empty.boardY() + boardSize-1 - empty.boardX()) % 2 == 1) {
			swapX = (int) (Math.random()*boardSize);
			swapY = (int) (Math.random()*boardSize);
			while (empty.boardX() == swapX && empty.boardY() == swapY) {
				swapX = (int) (Math.random()*boardSize);
				swapY = (int) (Math.random()*boardSize);
			}
			
			swap(empty,board[swapY][swapX],true);
			swap(board[0][0],board[1][1],true);
		}
	}
	
	public boolean isSolved() {
		for (int y=0, c=1; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {
				if (board[y][x].value() != c)
					return false;
				c++;
			}
		}
		return true;
	}
	
	public void move(Tile clicked) {
		if (empty.boardX() == clicked.boardX() || empty.boardY() == clicked.boardY()) {
			int dx = clicked.boardX() - empty.boardX(); 
			int dy = clicked.boardY() - empty.boardY();
			
			int iterate = Math.max(Math.abs(dx),Math.abs(dy));
			dx = Math.max(-1,Math.min(dx, 1));
			dy = Math.max(-1,Math.min(dy, 1));
			
			if (iterate > 0) {
				for (int i=0; i<iterate;i++) {
					Tile next = getTile(empty.boardX()+dx, empty.boardY()+dy);
					movingTiles.add(next);
					swap(empty,next,false);
				}
				moveCount++;
					
			}
		}
	}
	
	public void swap(Tile a, Tile b, boolean forMask) {
		board[a.boardY()][a.boardX()] = b;
		board[b.boardY()][b.boardX()] = a;
		
		Point temp = new Point(a.point().getX(),a.point().getY());
		
		if (forMask) {
			a.updateMask(b.point());
			b.updateMask(temp);
		}
		
		a.updatePoint(b.point());
		b.updatePoint(temp);
	}
	
	public Tile getTileFromPoint(Point point) {
		float percentX = point.getX()/tileSize;
		float percentY = point.getY()/tileSize;
		
		percentX = Math.max(0, Math.min(percentX,boardSize-1));
		percentY = Math.max(0, Math.min(percentY,boardSize-1));
		
		int x = (int) percentX;
		int y = (int) percentY;
		
		return board[y][x];
	}
	
	public Tile getTile(int boardX, int boardY) {
		if (boardX < 0 || boardX > boardSize-1 || boardY < 0 || boardY > boardSize-1)
			return null;
		return board[boardY][boardX];
	}
	
	public void tick() {
		
		for (int i=movingTiles.size()-1; i>=0; i--) {
			Tile current = movingTiles.get(i);
			if (!current.moveMask(12F))
				movingTiles.remove(current);
		}

	}
	
	public void render(Graphics g) {
		for (Tile[] i: board) {
			for (Tile t: i) {
				if (!t.isEmpty())
					t.render(g);
			}
		}
	}
	
	public float fontSize() {
		return fontSize;
	}
	
	public int moveCount() {
		return moveCount;
	}
}
