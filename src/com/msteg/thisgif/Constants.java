package com.msteg.thisgif;

import java.io.File;

import android.os.Environment;

public interface Constants {
	public final static String LOG = "************** this gif **************";
	public final static String CURRENT_TAB = "currentTab";
	public final static File ROOT_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ThisGif/");
	public final static File TMP_DIR = new File(ROOT_DIR.getAbsolutePath() + "/tmp/");
	
	public final static class MakeAGif {
		public final static String TAG = "makeAGif";
		
		public final static class Gif {
			public final static String SOURCE_URI = "sourceUri";
			public final static String THUMB = "thumbnail";
			public final static String TAGS = "tags";
			
		}
		
		public final static class Intents {
			public final static int TO_VIDEO_PICKER = 201;
		}
	}
	
	public final static class MyGifs {
		public final static String TAG = "myGifs";
	}
	
	public final static class BrowseGifs {
		public final static String TAG = "browseGifs";
	}
	
	public final static class GifEditor {
		public final static String TAG = "gifEditor";
		public final static class Intents {
			public final static int TO_GIF_EDITOR = 200;
		}
	}
}
