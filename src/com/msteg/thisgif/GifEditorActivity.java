package com.msteg.thisgif;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class GifEditorActivity extends SherlockFragmentActivity implements Constants, OnClickListener {
	ActionBar ab;
	Menu menu;
	
	ImageView viewer;
	Uri sourceVideo;
	Gallery thumbHolder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gif_editor_activity);
		
		ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getSupportMenuInflater();
		mi.inflate(R.menu.main_menu, menu);
    	return super.onCreateOptionsMenu(menu);
	}
    
    public boolean onOptionsItemSelected(MenuItem mi) {
    	switch(mi.getItemId()) {
    	case R.id.menu_preferences:
    		return true;
    	case R.id.menu_errorLog:
    		return true;
    	default:
    		return false;
    	}
    }
	
	@Override
	public void onClick(View v) {
		
		
	}

}
