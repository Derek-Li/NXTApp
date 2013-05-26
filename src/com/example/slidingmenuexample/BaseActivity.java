package com.example.slidingmenuexample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected ListFragment mFrag;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		mFrag = new MenuListPages();
		ft.replace(R.id.menu_frame, mFrag);
		ft.commit();

		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidth(20);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset(600);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

//	public class BasePagerAdapter extends FragmentPagerAdapter {
//		private List<Fragment> mFragments = new ArrayList<Fragment>();
//		private ViewPager mPager;
//
//		public BasePagerAdapter(FragmentManager fm, ViewPager vp) {
//			super(fm);
//			mPager = vp;
//			mPager.setAdapter(this);
//			for (int i = 0; i < 3; i++) {
//				addTab(new MenuListPages());
//			}
//		}
//
//		public void addTab(Fragment frag) {
//			mFragments.add(frag);
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//			return mFragments.get(position);
//		}
//
//		@Override
//		public int getCount() {
//			return mFragments.size();
//		}
//	}

}
