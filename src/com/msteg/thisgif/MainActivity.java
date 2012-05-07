package com.msteg.thisgif;

import java.util.List;
import java.util.Vector;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.msteg.thisgif.screens.BrowseGifsRoot;
import com.msteg.thisgif.screens.MakeAGifRoot;
import com.msteg.thisgif.screens.MyGifsRoot;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends SherlockFragmentActivity implements Constants, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
	TGPager tgPager;
	private ViewPager vp;
	private TabHost tabHost;
	ActionBar ab;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_Sherlock_Light);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);
        
        initTabHost(savedInstanceState);
		if(savedInstanceState != null)
			tabHost.setCurrentTabByTag(savedInstanceState.getString(CURRENT_TAB));
		
		initViewPager();		
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
	
    
    private void initViewPager() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, MakeAGifRoot.class.getName()));
		fragments.add(Fragment.instantiate(this, MyGifsRoot.class.getName()));
		fragments.add(Fragment.instantiate(this, BrowseGifsRoot.class.getName()));
		
		tgPager = new TGPager(super.getSupportFragmentManager(), fragments);
		vp = (ViewPager) super.findViewById(R.id.view_pager_root);
		vp.setAdapter(tgPager);
		vp.setOnPageChangeListener(this);
	}
	
	private void initTabHost(Bundle args) {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		TGTab tgtab = null;
		View tgindicator = null;
		
		addTab(this, tabHost, 
				tabHost.newTabSpec(MakeAGif.TAG)
				.setIndicator((tgindicator = new TGIndicator(getString(R.string.indicator_makeAGif), this).tab)),
				(tgtab = new TGTab(MakeAGif.TAG, MakeAGifRoot.class, args)));
		
		addTab(this, tabHost,
				tabHost.newTabSpec(MyGifs.TAG)
				.setIndicator((tgindicator = new TGIndicator(getString(R.string.indicator_myGifs), this).tab)),
				(tgtab = new TGTab(MyGifs.TAG, MyGifs.class, args)));
		
		addTab(this, tabHost,
				tabHost.newTabSpec(BrowseGifs.TAG)
				.setIndicator((tgindicator = new TGIndicator(getString(R.string.indicator_browseGifs), this).tab)),
				(tgtab = new TGTab(BrowseGifs.TAG, BrowseGifs.class, args)));
		
		tabHost.setOnTabChangedListener(this);
		
		if(!ROOT_DIR.exists())
			ROOT_DIR.mkdir();
		
		if(!TMP_DIR.exists())
			TMP_DIR.mkdir();
		
	}
	
	private static void addTab(MainActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TGTab tgtab) {
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(CURRENT_TAB, tabHost.getCurrentTab());
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onTabChanged(String tabId) {
		vp.setCurrentItem(tabHost.getCurrentTab());
	}

	@Override
	public void onPageScrollStateChanged(int state) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}

	@Override
	public void onPageSelected(int page) {
		tabHost.setCurrentTab(page);
	}
	
	private class TGIndicator {
		View tab;
		
		TGIndicator(String label, Context context) {
			tab = LayoutInflater.from(context).inflate(R.layout.tab_layout, null, false);
			TextView indicator = (TextView) tab.findViewById(R.id.tabIndicator);
			indicator.setText(label);
		}
	}
	
	private class TGTab {
		private String tabId;
		private Class<?> clz;
		private Bundle args;
		public View tab;
		
		TGTab(String tagId, Class<?> clz, Bundle args) {
			this.tabId = tagId;
			this.clz = clz;
			this.args = args;
			
		}
	}
	
	public class TabFactory implements TabHost.TabContentFactory {
		Context context;
		
		public TabFactory(Context context) {
			this.context = context;
		}
		
		@Override
		public View createTabContent(String tag) {
			View view = new View(context);
			view.setMinimumHeight(0);
			view.setMinimumWidth(0);
			return view;
		}
		
	}
    
    class TGPager extends FragmentStatePagerAdapter {
    	List<Fragment> fragments;
    	FragmentManager fm;
    	
    	public TGPager(FragmentManager fm, List<Fragment> fragments) {
    		super(fm);
    		this.fm = fm;
    		this.fragments = fragments;
    	}

    	@Override
    	public Fragment getItem(int position) {
    		return fragments.get(position);
    	}

    	@Override
    	public int getCount() {
    		return fragments.size();
    	}
    }
}