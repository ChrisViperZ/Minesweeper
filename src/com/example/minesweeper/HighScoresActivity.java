package com.example.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
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
		
		initScores();
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
		int easybound = scores.getInt("e4", 999);
		int hardbound = scores.getInt("h4", 999);
		RelativeLayout v = (RelativeLayout) findViewById(R.id.addscoretab);
		String cat = "not added";
		
		EditText et = (EditText) v.getChildAt(1); //get the new score -- THIS NUMBER IS HARDCODED; EDIT IF ACTIVITY_HIGH_SCORES.XML IS EDITED

		if (et.getText().length() != 0){ //makes sure theres actually something in the field
			
			int newscore = Integer.parseInt( et.getText().toString() );
			if(hardMode && newscore < hardbound){
				String key = "";
				//grab the top 4 hard scores 
				int [] arr = new int[4];
				for(int i = 0; i < 4; i++){
					key = "h".concat(Integer.toString(i));
					arr[i] = scores.getInt(key, 999);
				}
				reSort(arr, newscore);

			}
			else if(!hardMode && newscore < easybound){
				String key = "";
				//grab the top 4 easy scores 
				int [] arr = new int[4];
				for(int i = 0; i < 4; i++){
					key = "e".concat(Integer.toString(i));
					arr[i] = scores.getInt(key, 999);
				}
				reSort(arr, newscore);
			}
		}
		else { cat = "Enter valid score"; }
		
		cat = hardMode ? "hard: " : "easy: ";
		String m = hardMode ? "h" : "e";
		for (int j = 0; j < 5; j++){
			cat = cat.concat(Integer.toString(scores.getInt( m.concat(Integer.toString(j)), 999)));
			cat = cat.concat(" ");
		}
		
		TextView tv = (TextView) v.getChildAt(4); //get the data textview -- THIS NUMBER IS HARDCODED; EDIT IF ACTIVITY_HIGH_SCORES.XML IS EDITED
		tv.setText(cat);
	}
	
	public void reSort(int [] arr, int newscore){
		SharedPreferences scores = this.getSharedPreferences("scoreList", Context.MODE_PRIVATE);
		SharedPreferences.Editor adder = scores.edit();
		String mode = hardMode ? "h" : "e";
		String addkey = "";
		for (int i = 3; i >= 0; i--){
			addkey = mode.concat(Integer.toString(i+1));
			if(arr[i] > newscore){
				adder.putInt(addkey, arr[i]);
			}
			else{
				adder.putInt(addkey, newscore);
				adder.commit();
				return; //stop shifting values
			}
		}
		
		//if we've reached here, we need to push the newscore to the first position
		addkey = mode.concat(Integer.toString(0));
		adder.putInt(addkey, newscore);
		adder.commit();
	}
	
	public void resetScores(View view) {
		SharedPreferences scores = this.getSharedPreferences("scoreList", Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = scores.edit();
		prefsEditor.putInt("e0", 999);
		prefsEditor.putInt("e1", 999);
		prefsEditor.putInt("e2", 999);
		prefsEditor.putInt("e3", 999);
		prefsEditor.putInt("e4", 999);
		prefsEditor.putInt("h0", 999);
		prefsEditor.putInt("h1", 999);
		prefsEditor.putInt("h2", 999);
		prefsEditor.putInt("h3", 999);
		prefsEditor.putInt("h4", 999);
		prefsEditor.commit();
	}
	
	public void initScores(){
		SharedPreferences scores = this.getSharedPreferences("scoreList", Context.MODE_PRIVATE);
		
		if( !scores.contains("e0") ){
			TextView x = new TextView(this); //this might be unnecessary in the future, i just didn't know how to overcome the problem
	        resetScores(x);
		}
	}

}
