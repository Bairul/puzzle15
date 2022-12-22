package com.game15.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.game15.game.display.Display;
import com.game15.game.inputs.MouseManager;
import com.game15.game.states.GameState;
import com.game15.game.states.SettingState;
import com.game15.game.states.State;

public class Game implements Runnable {
	private boolean running;
	private Thread thread;
	
	//Display & render
	private Display display;
	private BufferStrategy bs;
	private Graphics g; 
	
	//States
	private State gameState;
	private State settingState;
	
	//inputs
	private MouseManager mouseManager;
	
	//timer
	private int cs = 0, sec = 0, min = 0, cd = 15;
	private boolean pause = true, inspection = false;
	private float fontSize;

	@Override
	public void run() {
		init();
		
		int fps = 60;
		double nsPerFrame = 1000000000/fps;
		double delta = 0;
		long now;
		long past = System.nanoTime();
		
		long timer = System.currentTimeMillis();
		
		while (running) {//game loop
			now = System.nanoTime();
			delta += (now-past)/nsPerFrame;
			past = now;
			if (delta >= 1) {
				tick();
				delta--;
			}
			render();
			
			if (!pause) {
				if (System.currentTimeMillis() - timer > 10) {
					timer += 10;
					cs++;
					if (cs >= 100) {
						cs = 0;
						sec++;
					} else if (sec >= 60) {
						sec = 0;
						min++;
					} else if (min >= 60) {
						System.out.println("Time out");
					}
				}
			} else if (inspection) {
				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					if (cd <= 1) {
						pause = false;
						inspection = false;
					}
					cd--;
				}
			} else
				timer = System.currentTimeMillis();
			
			try {
				thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop();
	}
	
	private void init() {
		display = new Display("15puzzle",128,5);
		gameState = new GameState(this);
		settingState = new SettingState(this);
		gameState.setState(gameState);
		mouseManager = new MouseManager();
		
		display.frame().addMouseListener(mouseManager);
		display.frame().addMouseMotionListener(mouseManager);
		display.canvas().addMouseListener(mouseManager);
		display.canvas().addMouseMotionListener(mouseManager);
		
		fontSize = height()/18F;
	}

	private void tick() {
		if (State.getState() != null)
			State.getState().tick();
	}
	
	private void render() {
		bs = display.canvas().getBufferStrategy();
		
		if (bs == null) {
			display.canvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width(), height());
		//start draw stuff
		if (State.getState() != null)
			State.getState().render(g);
		
		drawTime();
		//end draw stuff
		bs.show();
		g.dispose();
	}
	
	private void drawTime() {
		if (State.getState() != gameState)
			return;
		
		Font font = g.getFont();
		int x = (int) (fontSize*2);
		int y = (int) (height()/2-height()/8+fontSize/2);
		int xOffset = 14;
		
		if (inspection) {
			if (font.getSize() != fontSize) {
				Font newFont = font.deriveFont(fontSize);
				g.setFont(newFont);
			}
			if (cd <=3)
				g.setColor(Color.red);
			else if (cd <= 8)
				g.setColor(Color.black);
			else
				g.setColor(Color.green);
			
			if (cd/10 < 1)
				g.drawString(""+cd,width()-x*2+xOffset+7,y);
			else
				g.drawString(""+cd,width()-x*2+xOffset, y);
			
		} else {
			if (font.getSize() != fontSize-5) {
				Font newFont = font.deriveFont(fontSize-5F);
				g.setFont(newFont);
			}
			g.setColor(Color.black);
			if (cs/10 < 1)
				g.drawString(0+""+cs,width()-x+xOffset,y);
			else
				g.drawString(""+cs,width()-x+xOffset,y);
			
			if (sec/10 < 1)
				g.drawString(0+""+sec+"  :",width()-x*2+xOffset,y);
			else
				g.drawString(""+sec+"  :",width()-x*2+xOffset,y);
			
			if (min/10 < 1)
				g.drawString(0+""+min+"  :",width()-x*3+xOffset,y);
			else
				g.drawString(""+min+"  :",width()-x*3+xOffset,y);
		}
	}

	public synchronized void start() {
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setCursor(Cursor c) {
		display.frame().setCursor(c);
	}
	
	public MouseManager mouseManager() {
		return mouseManager;
	}
	
	public int mouseX() {
		return mouseManager.mouseX();
	}
	
	public int mouseY() {
		return mouseManager.mouseY();
	}
	
	public int width() {
		return display.width();
	}
	
	public int height() {
		return display.height();
	}
	
	public boolean pause() {
		return pause;
	}
	
	public void pause(boolean p) {
		pause = p;
	}
	
	public void inspection(boolean i) {
		inspection = i;
	}
	
	public void resetTime() {
		cs = 0;
		sec = 0;
		min = 0;
		cd = 15;
	}
	
	public void chgState() {
		if (State.getState() == gameState)
			State.setState(settingState);
		else
			State.setState(gameState);
	}
}
