package com.example.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private boolean hardMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		hardMode = intent.getBooleanExtra("hardMode", false);
		
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startGame(View view)
	{
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	public void options_click(View view)
	{
		Intent intent = new Intent(this, OptionsActivity.class);
		intent.putExtra("hardMode", hardMode);
		startActivity(intent);
	}
	
	public void highscores_click(View view)
	{
		Intent intent = new Intent(this, HighScoresActivity.class);
		startActivity(intent);
	}

}
