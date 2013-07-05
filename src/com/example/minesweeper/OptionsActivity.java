package com.example.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class OptionsActivity extends Activity {
	private boolean hardMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		//grab hardmode flag from intent and use data
		Intent intent = getIntent();
		hardMode = intent.getBooleanExtra("hardMode", false);
		String message = "Easy";
		if(hardMode)  { message = "Hard"; }
		
		TextView textViewToChange = (TextView) findViewById(R.id.options_text);
		textViewToChange.setText(message);
	}

	public void setEasy(View view){
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("hardMode", false);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //makes sure to use old MainActivity instead of creating a new instance
		startActivity(intent);
	}
	
	public void setHard(View view){
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("hardMode", true);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
