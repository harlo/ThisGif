package com.msteg.thisgif.mods;

import com.msteg.thisgif.Constants;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class TagButton extends Button implements Constants {
	Context context;
	
	public TagButton(Context context) {
		super(context);
		this.context = context;
	}
	
	public TagButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

}
