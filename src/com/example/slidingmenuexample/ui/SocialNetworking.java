package com.example.slidingmenuexample.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.slidingmenuexample.R;

public class SocialNetworking extends Activity {
	Button email, options, wikispacesbtn;
	ImageView instagram, facebook, twitter;

	@Override
	public void onCreate(Bundle SavedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.social_networking);

		instagram = (ImageView) findViewById(R.id.instagrambtn);
		facebook = (ImageView) findViewById(R.id.facebookbtn);
		twitter = (ImageView) findViewById(R.id.twitterbtn);
		options = (Button) findViewById(R.id.Options);
		wikispacesbtn = (Button) findViewById(R.id.wikispacesbtn);
		
		wikispacesbtn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				String url = "http://www.wikispaces.com/";
				Intent wikispacesIntent = new Intent(
						android.content.Intent.ACTION_VIEW);
				wikispacesIntent.setData(Uri.parse(url));
				startActivity(wikispacesIntent);
			}
		});

		facebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {

					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("fb://?ref=tn_tnmn"));
					startActivity(intent);

				} catch (Exception e) {

					startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse("http://www.facebook.com/?ref=tn_tnmn")));
				}
			}
		});

		twitter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "https://twitter.com/intent/tweet?source=webclient&text=TWEET+THIS!";
				Intent twitterIntent = new Intent(
						android.content.Intent.ACTION_VIEW);
				twitterIntent.setData(Uri.parse(url));
				startActivity(twitterIntent);
			}
		});

		instagram.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "https://instagram.com/accounts/login/";
				Intent instagramIntent = new Intent(
						android.content.Intent.ACTION_VIEW);
				instagramIntent.setData(Uri.parse(url));
				startActivity(instagramIntent);
			}
		});

		options.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent othersIntent = new Intent(
						android.content.Intent.ACTION_VIEW);
				startActivity(Intent.createChooser(othersIntent, "Choose one"));
			}
		});
	}
}
