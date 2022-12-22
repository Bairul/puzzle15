package com.game15.game.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener,MouseMotionListener {
	private boolean leftPressed,leftClicked;
	private int mouseX,mouseY;
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isLeftClicked() {
		return leftClicked;
	}
	
	public int mouseX() {
		return mouseX;
	}
	
	public int mouseY() {
		return mouseY;
	}
	
	public void setLeftClicked(boolean l) {
		leftClicked = l;
	}
	//implemented
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = true;
			leftClicked = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = false;
			leftClicked = true;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		leftClicked = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
