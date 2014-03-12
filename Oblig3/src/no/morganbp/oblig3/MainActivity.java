package no.morganbp.oblig3;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class MainActivity extends FragmentActivity {

	public static final String EXTRA_MESSAGE = "no.morganbp.MainActivity";
	public static boolean loadingData = false;

	ActionBar actionBar;

	SwipeAdapter swAdapter;
	ViewPager swipePager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swipe_view_pager);
		swAdapter = new SwipeAdapter(getSupportFragmentManager());
		swipePager = (ViewPager) findViewById(R.id.swipe_pager);
		swipePager.setAdapter(swAdapter);
		if(android.os.Build.VERSION.SDK_INT >= 11){
			Log.d("hei","hei");
			initActionBar();
			
		}
		
	}

	private void initActionBar() {
		
		swipePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {					
			}
		});
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				swipePager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}
		};
			
		
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("IMDB movie Search").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Norwegian Flag").setTabListener(tabListener));
		actionBar.show();	
		
	}

	public class SwipeAdapter extends FragmentPagerAdapter {
		private static final int SIZE = 2;

		public SwipeAdapter(FragmentManager fm) {
			super(fm);
		}
			
		
		
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new IMDBMovieFragment();
			case 1:
				return new FlagFragment();
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SIZE;
		}

	}
}