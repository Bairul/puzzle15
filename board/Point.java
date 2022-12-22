package com.game15.game.board;

public class Point {
	private float x,y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveTowards(Point destination, float speed) {
//		if (this.approximates(destination, speed))
//			return;
		
		float dx = destination.getX() - x;
		float dy = destination.getY() - y;
		
		float direction = (float) Math.atan2(dy, dx);
		float vx = (float) Math.cos(direction);
		float vy = (float) Math.sin(direction);
		
		x += vx*speed;
		y += vy*speed;
	}
	
	public boolean approximates(Point other, float speed) {
		float dx = other.getX() - x;
		float dy = other.getY() - y;
		
		float magnitude = (float) Math.sqrt(dx*dx+dy*dy);
		if (magnitude < speed/2) {
			x = other.getX();
			y = other.getY();
			return true;
		}
		return false;
	}
	
	public boolean isEquals(Point other) {
		return other.getX() == x && other.getY() == y;
	}
	
	public Point copyPoint(Point original) {
		return new Point(original.getX(), original.getY());
	}
	
	public void setPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}
	
	public int intX() {
		return Math.round(x);
	}
	
	public int intY() {
		return Math.round(y);
	}
	
	public String toString() {
		return "("+x+", "+y+")";
	}
}
