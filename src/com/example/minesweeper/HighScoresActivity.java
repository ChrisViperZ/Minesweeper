package com.example.minesweeper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
		updateEasy();
		updateHard();
	}
	
	public void onModeClicked(View view) {
		//"on" represents hard; "off" represents easy
		hardMode = ((ToggleButton) view).isChecked();		
	}
	
	//adds a score into the EASY or HARD high score list, if it is in the top 5
	public void addScore(View view) {
		SharedPreferences scores = getSharedPreferences("scoreList", 0);
		int easybound = scores.getInt("e4", 999);
		int hardbound = scores.getInt("h4", 999);
		RelativeLayout v = (RelativeLayout) findViewById(R.id.addscoretab); //we need this here because things are hardcoded because i am bad
		String cat = "";
		
		EditText et = (EditText) v.getChildAt(1); //get the new score -- THIS NUMBER IS HARDCODED; EDIT IF ACTIVITY_HIGH_SCORES.XML IS EDITED

		if (et.getText().length() != 0){ //makes sure theres actually something in the field...but the else statement seems to never be triggered
			
			int newscore = Integer.parseInt( et.getText().toString() );
			
			//criteria for adding a new hard high score 
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
			
			//criteria for adding a new easy high score
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

		//update necessary tabs and display a message
		if (hardMode) { updateHard(); }
		else { updateEasy(); }
		
		String m = hardMode ? "hard" : "easy";
		cat = "Updated " + m + " high scores.";
		
		TextView tv = (TextView) v.getChildAt(4); //get the data textview -- THIS NUMBER IS HARDCODED; EDIT IF ACTIVITY_HIGH_SCORES.XML IS EDITED
		tv.setText(cat);
	}
	
	
	/* Resets both easy and hard high scores.
	 */
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
		
		updateEasy();
		updateHard();
	}
	
	/* Initializes scores if this is the first time they're opening the application.
	 */
	public void initScores(){
		SharedPreferences scores = this.getSharedPreferences("scoreList", Context.MODE_PRIVATE);
		
		if( !scores.contains("e0") ){
			TextView x = new TextView(this); //this might be unnecessary in the future, i just didn't know how to overcome the problem
	        resetScores(x);
		}
	}
	
	/* Sorts and inserts a new score into a corresponding HARD/EASY high score saved data.
	 * INPUT: array of integers representing the top 4 scores, the new score to be added
	 */
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
	
	/* Updates the display contents of the easy high scores into the easy tab.
	 */
	public void updateEasy(){
		SharedPreferences scores = this.getSharedPreferences("scoreList", Context.MODE_PRIVATE);
		LinearLayout easytab = (LinearLayout) findViewById(R.id.easytab);
		final ListView easyscores = (ListView) easytab.getChildAt(0); // THIS NUMBER IS HARDCODED, EDIT IF ACTIVITY_HIGH_SCORES.XML IS EDITED
	    ArrayList<String> list = new ArrayList<String>();
	    String m = "e";
	    for (int i = 0; i < 5; i++){
	    	list.add((Integer.toString(scores.getInt( m.concat(Integer.toString(i)), 999))).concat(" s"));
	    }
	
	    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
	    easyscores.setAdapter(adapter);
	}
	
	/* Updates the display contents of the hard high scores into the hard tab.
	 */
	public void updateHard(){
		SharedPreferences scores = this.getSharedPreferences("scoreList", Context.MODE_PRIVATE);
		LinearLayout hardtab = (LinearLayout) findViewById(R.id.hardtab);
		final ListView hardscores = (ListView) hardtab.getChildAt(0); // THIS NUMBER IS HARDCODED, EDIT IF ACTIVITY_HIGH_SCORES.XML IS EDITED
	    ArrayList<String> list = new ArrayList<String>();
	    String m = "h";
	    for (int i = 0; i < 5; i++){
	    	list.add((Integer.toString(scores.getInt( m.concat(Integer.toString(i)), 999))).concat(" s"));
	    }
	
	    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
	    hardscores.setAdapter(adapter);	
	}
	

}
