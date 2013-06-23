package com.example.minesweeper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.ToggleButton;

public class HighScoresActivity extends Activity {
	private boolean hardMode = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
		
		TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec1=tabHost.newTabSpec("easytab");
		spec1.setIndicator("Easy");
		spec1.setContent(R.id.easytab);


		TabSpec spec2=tabHost.newTabSpec("hardtab");
		spec2.setIndicator("Hard");
		spec2.setContent(R.id.hardtab);

		TabSpec spec3=tabHost.newTabSpec("addscoretab");
		spec3.setIndicator("Add Scores");
		spec3.setContent(R.id.addscoretab);
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
		
		SharedPreferences myPrefs = this.getSharedPreferences("scoreList", 0);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putString("string", "Superduper");
        prefsEditor.putInt("int", 5);
        prefsEditor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}

	public void onModeClicked(View view) {
		//"on" represents hard; "off" represents easy
		hardMode = ((ToggleButton) view).isChecked();		
	}
	
	public void addScore(View view) {
		//TODO:: HERE score_entry id
		SharedPreferences scores = getSharedPreferences("scoreList", 0);
		String test = scores.getString("string", "NE");
		int number = scores.getInt("int", 0);
		
		//RelativeLayout v = (RelativeLayout) findViewById(R.id.addscoretab);
		
		
	}
	
	public void resetScores(View view) {
		//TODO:: HERE
	}

}
