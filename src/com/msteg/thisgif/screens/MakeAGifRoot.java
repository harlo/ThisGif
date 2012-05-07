package com.msteg.thisgif.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.msteg.thisgif.Constants;
import com.msteg.thisgif.GifEditorActivity;
import com.msteg.thisgif.R;
import com.msteg.thisgif.R.layout;
import com.msteg.thisgif.mods.TagButton;
import com.msteg.thisgif.utils.MediaRes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MakeAGifRoot extends Fragment implements Constants, OnClickListener {
	View fragment;
	ImageButton loadVideo, addTag;
	EditText addTagText;
	Button toEditor, saveGif;
	LinearLayout tagHolderRoot, addedTagHolder;
	
	public HashMap<String, Object> properties;
	Activity a;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		properties = new HashMap<String, Object>();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater li, ViewGroup container, Bundle savedInstanceState) {
		fragment = li.inflate(R.layout.make_a_gif_root, container, false);
		
		loadVideo = (ImageButton) fragment.findViewById(R.id.makeAGif_loadVideo);
		loadVideo.setOnClickListener(this);
		
		toEditor = (Button) fragment.findViewById(R.id.makeAGif_toEditor);
		toEditor.setOnClickListener(this);
		
		tagHolderRoot = (LinearLayout) fragment.findViewById(R.id.makeAGif_tagEditorHolder);
		
		addedTagHolder = (LinearLayout) fragment.findViewById(R.id.makeAGif_addedTagHolder);
		addTagText = (EditText) fragment.findViewById(R.id.makeAGif_addTagText);
		
		addTag = (ImageButton) fragment.findViewById(R.id.makeAGif_addTagButton);
		addTag.setOnClickListener(this);
		
		saveGif = (Button) fragment.findViewById(R.id.makeAGif_saveGif);
		saveGif.setOnClickListener(this);
		
		updateThumb();
		
		return fragment;
	}
	
	private void updateThumb() {
		if(properties.containsKey(MakeAGif.Gif.THUMB))
			loadVideo.setImageBitmap((Bitmap) properties.get(MakeAGif.Gif.THUMB));
		else
			loadVideo.setImageDrawable(a.getResources().getDrawable(R.drawable.ic_launcher));
	}
	
	@SuppressWarnings("unchecked")
	private void addTag(String tag) {
		ArrayList<String> tags = (ArrayList<String>) properties.get(MakeAGif.Gif.TAGS);
		
		if(tags == null) {
			properties.put(MakeAGif.Gif.TAGS, new ArrayList<String>());
			tags = (ArrayList<String>) properties.get(MakeAGif.Gif.TAGS);
		}
		
		if(!tags.contains(tag))
			tags.add(tag);
		
		addTagText.setText("");
		TagButton tb = new TagButton(a);
		tb.setText(tag);
		tb.setOnClickListener(this);
		addedTagHolder.addView(tb);
	}
	
	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		this.a = a;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			switch(requestCode) {
			case MakeAGif.Intents.TO_VIDEO_PICKER:
				properties.put(MakeAGif.Gif.SOURCE_URI, data.getData());
				properties.put(MakeAGif.Gif.THUMB, MediaRes.getThumbFromVideo(data.getData(), a));
				updateThumb();
				break;
			case GifEditor.Intents.TO_GIF_EDITOR:
				break;
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v == loadVideo) {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("video/*");
			startActivityForResult(intent, MakeAGif.Intents.TO_VIDEO_PICKER);
		} else if(v == toEditor) {
			Intent intent = new Intent(a, GifEditorActivity.class);
			intent.putExtra(MakeAGif.Gif.SOURCE_URI, (Uri) properties.get(MakeAGif.Gif.SOURCE_URI));
			startActivityForResult(intent, GifEditor.Intents.TO_GIF_EDITOR);
		} else if(v == addTag) {
			if(addTagText.getText().toString().length() >= 2)
				addTag(addTagText.getText().toString());
		} else if(v == saveGif) {
			
		} else if(v instanceof TagButton) {
			ArrayList<String> tags = (ArrayList<String>) properties.get(MakeAGif.Gif.TAGS);
			tags.remove(((TagButton) v).getText().toString());
			addedTagHolder.removeView(v);
			
		}
		
	}

}
