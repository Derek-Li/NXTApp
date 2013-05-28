/**
 *
 * @author sleepygarden
 *
 */
package com.example.slidingmenuexample;

import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageCache {
	
	private static final String TAG = "MyActivity";
	public static ImageCache instance;
	private static HashMap<String, Drawable> map;

	public static ImageCache getInstance() {
		if (instance == null) {
			instance = new ImageCache();
			map = new HashMap<String, Drawable>();
		}
		return instance;
	}

	public Drawable getDrawable(String ref, Context c) {
		if (map.containsKey(ref)) { //contains
			Log.d("TAG", "IF CALLED");
			return map.get(ref);
		} else {
			int resID = c.getResources().getIdentifier(
					c.getPackageName() + ":drawable/" + ref, null,
					null); //write method to get string for each drawable image
			Drawable d = c.getResources().getDrawable(resID);
			Log.d("TAG", "ELSE CALLED");
			map.put(ref, d);
			return d;
			
		}
	}
}