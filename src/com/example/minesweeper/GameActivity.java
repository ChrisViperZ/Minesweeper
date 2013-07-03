package com.example.minesweeper;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	private static int ROW = 9;
	private static int COL = 9;
	
	private static int BLOCKSIZE = 40;

	private int mines = 10;

	int total;

	int time = 0;

	Block block[][] = new Block[COL][ROW];
	TextView tgrid[][] = new TextView[COL][ROW];

	boolean inUse[][] = new boolean[COL][ROW];
	boolean isFlagged[][] = new boolean[COL][ROW];
	int surrounding[][] = new int[COL][ROW];

	boolean isHappy = true;
	boolean gameOver = false;
	boolean isFlag = false;

	int count = 0;

	Timer timer = new Timer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		total = ROW * COL - mines;
		System.out.print(total);
		FillGame();
		PrintBoard();

		timer.schedule(new GameTimerTask(), 0, 1000);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		clearBoard();
		
	}

	class GameTimerTask extends TimerTask {
		@Override
		public void run() {
			if (!gameOver)
				time++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	public void FillGame() {
		FillBoard();
		FillSurround();
	}

	public void clearBoard() {
		Block b;
		TextView tv;

		int count = 0;
		int xmargin = 0, ymargin = 0;
		
		RelativeLayout gv = (RelativeLayout) findViewById(R.id.grid);
		RelativeLayout.LayoutParams rel_b;
		

		


		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
								
				rel_b = new RelativeLayout.LayoutParams(BLOCKSIZE, BLOCKSIZE);
				rel_b.leftMargin = xmargin;
				rel_b.topMargin = ymargin;

				b = new Block(this);
				b.setId(count);
				b.setLayoutParams(rel_b);
				b.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Block b = (Block) v;
						block[b.getyPos()][b.getxPos()].setVisibility(4);
						tgrid[b.getyPos()][b.getxPos()].setVisibility(0);
					}
				});

				gv.addView(b);
				
				

				tv = new TextView(this);
				tv.setLayoutParams(rel_b);
				tv.setVisibility(4);
				
				tv.setText(Integer.toString(surrounding[i][j]));
				tv.setGravity(Gravity.CENTER);
				gv.addView(tv);
				
				b.setxPos(j);
				b.setyPos(i);
				
				block[i][j] = b;
				tgrid[i][j] = tv;
				
				count++;
				xmargin += BLOCKSIZE;
			}
			ymargin += BLOCKSIZE;
			xmargin = 0;
		}
		
		int bSize = gv.getMeasuredWidth() / COL;
		
		System.out.println(bSize);
		
		
	}

	public void FillBoard() {
		int i = 0;
		int colRand, rowRand;
		Random rand = new Random();

		while (i < mines) {
			colRand = rand.nextInt(COL);
			rowRand = rand.nextInt(ROW);

			if (!inUse[colRand][rowRand])
				inUse[colRand][rowRand] = true;
			else
				continue;

			i++;
		}

	}

	public int CheckSurround(int r, int c) {
		int count = 0;

		if (r - 1 >= 0) {
			if (inUse[c][r - 1])
				count++;

			if (c - 1 >= 0)
				if (inUse[c - 1][r - 1])
					count++;
			if (c + 1 < COL)
				if (inUse[c + 1][r - 1])
					count++;
		}

		if (r + 1 < ROW) {
			if (inUse[c][r + 1])
				count++;

			if (c - 1 >= 0)
				if (inUse[c - 1][r + 1])
					count++;
			if (c + 1 < COL)
				if (inUse[c + 1][r + 1])
					count++;
		}

		if (c - 1 >= 0)
			if (inUse[c - 1][r])
				count++;

		if (c + 1 < COL)
			if (inUse[c + 1][r])
				count++;

		return count;
	}

	public void FillSurround() {

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				surrounding[i][j] = CheckSurround(j, i);
			}
		}
	}

	public void PrintBoard() {
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				System.out.print(surrounding[j][i] + " ");
			}
			System.out.println();
		}

		int test;
		System.out.println();

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				if (inUse[j][i])
					test = 1;
				else
					test = 0;
				System.out.print(test + " ");
			}
			System.out.println();
		}
	}
}
