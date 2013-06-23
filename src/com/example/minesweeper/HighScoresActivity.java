package com.example.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HighScoresActivity extends Activity {

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

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}

}
