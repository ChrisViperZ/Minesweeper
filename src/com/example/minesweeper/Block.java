package com.example.minesweeper;

import android.content.Context;
import android.widget.Button;

public class Block extends Button{

	public Block(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private int xPos = 0;
	private int yPos = 0;

	private boolean inUse = false;;
	private boolean isClicked = false;
	private boolean isFlagged = false;
	private int surrounding = 0;

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public boolean getClicked() {
		return isClicked;
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

	public void setxPos(int nx) {
		xPos = nx;
	}

	public void setyPos(int ny) {
		yPos = ny;
	}

	public void setClicked(boolean nClick){
		isClicked = nClick;
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
