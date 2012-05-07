package com.msteg.thisgif.utils;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

public class MediaRes {
	
	public static File URIToFile(Uri uri, Context c) {
		if(uri.getScheme() != null && uri.getScheme().equals("file"))
			return new File(uri.toString());
		else {
			String[] cols = {MediaStore.Video.Media.DATA};
			Cursor cur = c.getContentResolver().query(uri, cols, null, null, null);
			if(cur != null && cur.getCount() == 1) {
				cur.moveToFirst();
				return new File(cur.getString(cur.getColumnIndex(cols[0])));
			} else
				return null;
		}
	}
	
	public static Bitmap getThumbFromVideo(Uri videoUri, Context c) {
		return ThumbnailUtils.createVideoThumbnail(URIToFile(videoUri, c).getAbsolutePath(), MediaStore.Images.Thumbnails.MINI_KIND);
	}
}
