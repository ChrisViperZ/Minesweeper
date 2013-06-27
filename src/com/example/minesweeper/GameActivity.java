package com.example.minesweeper;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	private static int ROW = 9;
	private static int COL = 9;

	private int mines = 10;

	int total;

	int time = 0;

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

		int xmargin = 0;
		int ymargin = 0;

		Button b = new Button(this);

		RelativeLayout gv = (RelativeLayout) findViewById(R.id.grid);
		RelativeLayout.LayoutParams rel_b;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				b = new Button(this);
				rel_b = new RelativeLayout.LayoutParams(35, 35);
				rel_b.leftMargin = xmargin;
				rel_b.topMargin = ymargin;
				b.setLayoutParams(rel_b);
				gv.addView(b);
				xmargin += 35;
			}
			ymargin += 35;
			xmargin = 0;
		}

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
		Button b;
		TextView tv;
		String id;

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				b = new Button(GameActivity.this);
				tv = new TextView(GameActivity.this);

				id = new String();

				if (i > 9)
					id += Integer.toString(i);
				else
					id += "0" + Integer.toString(i);

				if (j > 9)
					id += Integer.toString(j);
				else
					id += "0" + Integer.toString(j);

				tv.setId(Integer.parseInt(id));
				tv.setVisibility(0);

				b.setId(Integer.parseInt(id));
				b.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						int cCOL, cROW;
						if (!isFlag) {
							// Flag icon not selected

							v.setVisibility(0);
							if (gameOver)
								return;

							if (Integer.parseInt(Integer.toString(v.getId())) < 1000) {
								cCOL = Integer.parseInt(Integer.toString(
										v.getId()).substring(0, 1));
								cROW = Integer.parseInt(Integer.toString(
										v.getId()).substring(1, 3));
							} else {
								cCOL = Integer.parseInt(Integer.toString(
										v.getId()).substring(0, 2));
								cROW = Integer.parseInt(Integer.toString(
										v.getId()).substring(2, 4));
							}
							if (isFlagged[cCOL][cROW])
								return;

							if (inUse[cCOL][cROW]) {
								// Game Over
								gameOver = true;

							}

							if (surrounding[cCOL][cROW] != 0) {
								// TextView tv = (TextView) findViewbyId();

							} else {

							}

						} else {
							// Flag icon is selected
						}

					}
				});

			}
		}

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
