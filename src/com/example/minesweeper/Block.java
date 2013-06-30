package com.example.minesweeper;

public class Block {

	private int x = 0;
	private int y = 0;

	private boolean inUse = false;;
	private boolean isFlagged = false;
	private int surrounding = 0;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getUse() {
		return inUse;
	}

	public boolean getFlagged() {
		return isFlagged;
	}

	public int getSurrounding() {
		return surrounding;
	}

	public void setX(int nx) {
		x = nx;
	}

	public void setY(int ny) {
		y = ny;
	}

	public void setUse(boolean nUse) {
		inUse = nUse;
	}

	public void setFlagged(boolean nFlag) {
		isFlagged = nFlag;
	}

	public void setSurrounding(int nSurrounding) {
		surrounding = nSurrounding;
	}

}
