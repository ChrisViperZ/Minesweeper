package com.example.minesweeper;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	private static int ROW = 9;
	private static int COL = 9;

	private static int BLOCKSIZE = 40;

	private int mines = 10;
	private boolean hardMode = false;

	int total;

	int time = 0;

	Block block[][];
	TextView tgrid[][];

	boolean inUse[][];
	boolean isFlagged[][];
	int surrounding[][] = new int[COL][ROW];

	boolean isHappy = true;
	boolean gameOver = false;
	boolean isFlag = false;
	boolean isStarted = false;

	int count = 0;

	Timer timer = new Timer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		total = ROW * COL - mines;
		FillGame();
		PrintBoard();

		final Runnable setTime = new Runnable() {

			@Override
			public void run() {
				((TextView) findViewById(R.id.timeView)).setText(Integer
						.toString(time));
			}

		};

		TimerTask timeTask = new TimerTask() {

			@Override
			public void run() {
				if (!gameOver && isStarted) {
					time++;
					runOnUiThread(setTime);
				}
			}

		};

		timer.schedule(timeTask, 0, 1000);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();
		hardMode = intent.getBooleanExtra("hardMode", false);

		newBoard();

	}

	public void faceClick(View view) {
		clearBoard();
		gameOver = false;
		isStarted = false;
		time = 0;
		((TextView) findViewById(R.id.timeView))
				.setText(Integer.toString(time));
		((Button) findViewById(R.id.face)).setText("H");
	}

	public void clearBoard() {
		FillGame();

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				block[i][j].setClicked(false);
				block[i][j].setVisibility(View.VISIBLE);
				tgrid[i][j].setVisibility(View.INVISIBLE);
				if (surrounding[i][j] == 0)
					tgrid[i][j].setText("");
				else
					tgrid[i][j].setText(Integer.toString(surrounding[i][j]));

			}
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

	public void zeroFlood(int r, int c) { // NOTE: R AND C ARE SWITCHED
		if (r == 0) { // top row
			if (c == 0) { // left top side
				clickIfUnclicked(r, c + 1);
				clickIfUnclicked(r + 1, c);
				clickIfUnclicked(r + 1, c + 1);
			} else if (c == COL - 1) { // right top side
				clickIfUnclicked(r, c - 1);
				clickIfUnclicked(r + 1, c - 1);
				clickIfUnclicked(r + 1, c);
			} else { // middle top
				clickIfUnclicked(r, c - 1);
				clickIfUnclicked(r, c + 1);
				clickIfUnclicked(r + 1, c - 1);
				clickIfUnclicked(r + 1, c);
				clickIfUnclicked(r + 1, c + 1);
			}
		}

		else if (r == ROW - 1) { // bottom row
			if (c == 0) { // left bottom side
				clickIfUnclicked(r - 1, c);
				clickIfUnclicked(r - 1, c + 1);
				clickIfUnclicked(r, c + 1);
			} else if (c == COL - 1) { // right bottom side
				clickIfUnclicked(r - 1, c - 1);
				clickIfUnclicked(r - 1, c);
				clickIfUnclicked(r, c - 1);
			} else { // middle bottom
				clickIfUnclicked(r - 1, c - 1);
				clickIfUnclicked(r - 1, c);
				clickIfUnclicked(r - 1, c + 1);
				clickIfUnclicked(r, c - 1);
				clickIfUnclicked(r, c + 1);

			}
		}

		else if (c == 0) { // left middle side
			clickIfUnclicked(r - 1, c);
			clickIfUnclicked(r - 1, c + 1);
			clickIfUnclicked(r, c + 1);
			clickIfUnclicked(r + 1, c);
			clickIfUnclicked(r + 1, c + 1);
		} else if (c == COL - 1) { // right middle side
			clickIfUnclicked(r - 1, c - 1);
			clickIfUnclicked(r - 1, c);
			clickIfUnclicked(r, c - 1);
			clickIfUnclicked(r + 1, c - 1);
			clickIfUnclicked(r + 1, c);
		}

		else { // all other cases
			clickIfUnclicked(r - 1, c - 1);
			clickIfUnclicked(r - 1, c);
			clickIfUnclicked(r - 1, c + 1);
			clickIfUnclicked(r, c - 1);
			clickIfUnclicked(r, c + 1);
			clickIfUnclicked(r + 1, c - 1);
			clickIfUnclicked(r + 1, c);
			clickIfUnclicked(r + 1, c + 1);
		}
	}

	public void clickIfUnclicked(int r, int c) {
		if (!block[r][c].getClicked())
			block[r][c].performClick();
	}

	public void newBoard() {
		Block b;
		TextView tv;

		int xmargin = 0, ymargin = 0;

		RelativeLayout gv = (RelativeLayout) findViewById(R.id.grid);
		RelativeLayout.LayoutParams rel_b;

		block = new Block[COL][ROW];
		tgrid = new TextView[COL][ROW];

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
						b.setClicked(true);

						isStarted = true;

						if (gameOver || isFlagged[b.getyPos()][b.getxPos()])
							return;

						if (inUse[b.getyPos()][b.getxPos()]) {
							gameOver = true;
							System.out.println("Boom");
							((Button) findViewById(R.id.face)).setText("S");
							return;
						}
						// test for zeroflooding-------
						if (surrounding[b.getyPos()][b.getxPos()] == 0) {
							zeroFlood(b.getyPos(), b.getxPos());
						}
						// end test for zeroflooding------
						block[b.getyPos()][b.getxPos()]
								.setVisibility(View.INVISIBLE);
						tgrid[b.getyPos()][b.getxPos()]
								.setVisibility(View.VISIBLE);

						count++;

						if (count == total) {
							// Win
							((Button) findViewById(R.id.face)).setText("W");
							gameOver = true;
							// attempt highscore adding
							System.out.print("Time was " + time);
							popUpScreen(addScoreFromGame(time));

						}

					}
				});

				gv.addView(b);

				tv = new TextView(this);
				tv.setLayoutParams(rel_b);
				tv.setVisibility(4);

				if (surrounding[i][j] == 0)
					tv.setText("");
				else
					tv.setText(Integer.toString(surrounding[i][j]));
				tv.setGravity(Gravity.CENTER);
				gv.addView(tv);

				b.setxPos(j);
				b.setyPos(i);

				block[i][j] = b;
				tgrid[i][j] = tv;

				xmargin += BLOCKSIZE;
			}
			ymargin += BLOCKSIZE;
			xmargin = 0;
		}
	}

	/*
	 * Creates and adds mines to the board.
	 */
	public void FillBoard() {
		int i = 0;
		int colRand, rowRand;
		Random rand = new Random();

		inUse = new boolean[COL][ROW];
		isFlagged = new boolean[COL][ROW];

		count = 0;

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

	/*
	 * Check the immediate surroundings (8 or less grids) at a position [c,r]
	 * and return the number of mines surrounding it. INPUT: Row position,
	 * Column position OUTPUT: Number of mines surrounding the position
	 */
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

	/*
	 * Populate the number board based on mine position. Uses CheckSurround().
	 */
	public void FillSurround() {

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {

				if (inUse[i][j])
					surrounding[i][j] = 9;
				else
					surrounding[i][j] = CheckSurround(j, i);
			}
		}
	}

	public void PrintBoard() {
		System.out.println("SURROUNDING");

		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				System.out.print(surrounding[i][j] + " ");
			}
			System.out.println();
		}

		int test;
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println("BOMBS 1 = BOMB");
		for (int i = 0; i < COL; i++) {
			for (int j = 0; j < ROW; j++) {
				if (inUse[i][j])
					test = 1;
				else
					test = 0;
				System.out.print(test + " ");
			}
			System.out.println();
		}
	}

	/*
	 * Adds a score into the EASY or HARD high score list, if it is in the top 5
	 * INPUT: New score to possibly be added OUTPUT: TRUE if a score was
	 * inserted, FALSE if not.
	 */
	public boolean addScoreFromGame(int newscore) {
		SharedPreferences scores = getSharedPreferences("scoreList", 0);
		int easybound = scores.getInt("e4", 999);
		int hardbound = scores.getInt("h4", 999);

		// criteria for adding a new hard high score
		if (hardMode && newscore < hardbound) {
			String key = "";
			// grab the top 4 hard scores
			int[] arr = new int[4];
			for (int i = 0; i < 4; i++) {
				key = "h".concat(Integer.toString(i));
				arr[i] = scores.getInt(key, 999);
			}
			reSort(arr, newscore);
			return true;
		}

		// criteria for adding a new easy high score
		else if (!hardMode && newscore < easybound) {
			String key = "";
			// grab the top 4 easy scores
			int[] arr = new int[4];
			for (int i = 0; i < 4; i++) {
				key = "e".concat(Integer.toString(i));
				arr[i] = scores.getInt(key, 999);
			}
			reSort(arr, newscore);
			return true;
		}

		return false;
	}

	/*
	 * Sorts and inserts a new score into a corresponding HARD/EASY high score
	 * saved data. INPUT: array of integers representing the top 4 scores, the
	 * new score to be added
	 */
	public void reSort(int[] arr, int newscore) {
		SharedPreferences scores = this.getSharedPreferences("scoreList",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor adder = scores.edit();
		String mode = hardMode ? "h" : "e";
		String addkey = "";
		for (int i = 3; i >= 0; i--) {
			addkey = mode.concat(Integer.toString(i + 1));
			if (arr[i] > newscore) {
				adder.putInt(addkey, arr[i]);
			} else {
				adder.putInt(addkey, newscore);
				adder.commit();
				return; // stop shifting values
			}
		}

		// if we've reached here, we need to push the newscore to the first
		// position
		addkey = mode.concat(Integer.toString(0));
		adder.putInt(addkey, newscore);
		adder.commit();
	}

	/*
	 * Creates a popup dialog INPUT: boolean of whether or not they made high
	 * score
	 */
	public void popUpScreen(boolean madeHS) {
		final Context context = this;
		AlertDialog.Builder popup = new AlertDialog.Builder(this);
		popup.setTitle("You won!");

		popup.setMessage(
				madeHS ? "Congratulations, you've made the top 5 high scores!"
						: "Try again to beat the top 5 high scores!")
				.setPositiveButton("Play Again",
						new DialogInterface.OnClickListener() {
							// resets game
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								((Button) findViewById(R.id.face))
										.performClick();
							}
						})
				.setNegativeButton("View High Scores",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(context,
										HighScoresActivity.class);
								startActivity(intent);
							}
						});

		AlertDialog popupScreen = popup.create();
		popupScreen.show();
	}
}
