package com.example.testlistview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		AnimationSet set;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
		
		ListView mlistview = (ListView) rootView.findViewById(R.id.listview1);
		String[] members = { "0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, members);
		mlistview.setAdapter(mAdapter);
		
		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(mlistview.getWidth(),MeasureSpec.AT_MOST);
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View listItem = mAdapter.getView(i, null, mlistview);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams listparams = mlistview.getLayoutParams();
		listparams.height = totalHeight + (mlistview.getDividerHeight() * (mAdapter.getCount() - 1));
		
		
		ScrollView mSview = (ScrollView)rootView.findViewById(R.id.scrollview1);
		ViewGroup.LayoutParams params = mSview.getLayoutParams();
		
		params.height=640;
		//mlistview.setLayoutParams(params);
		//mlistview.setScaleY(640);
		new Thread(new Runnable(){
			@Override
			public void run(){
				getActivity().runOnUiThread(new Runnable() {
					@TargetApi(Build.VERSION_CODES.HONEYCOMB)
					@Override
					public void run() {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
						}
						ListView mlistview = (ListView) getView().findViewById(R.id.listview1);
						//ViewGroup.LayoutParams params = mlistview.getLayoutParams();
						//mlistview.setLayoutParams(params);
						//mlistview.setScaleY(600);
					}
				});
			}
			}).start();
		
		//LinearLayout linearList = (LinearLayout)rootView.findViewById(R.id.linear1);
		//ScrollView mScrollView = (ScrollView)rootView.findViewById(R.id.scrollview1);
		//mScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,100));
			
		/*WebView mWebView = (WebView)rootView.findViewById(R.id.listview1);
		mWebView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,5000));
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.loadUrl("http://yahoo.co.jp");*/
			Log.d("myapp","runonthread");
			
			
			//http://zawapro.com/?p=348
			//http://www.atmarkit.co.jp/ait/articles/1009/15/news120_2.html
			//http://anz-note.tumblr.com/post/87698668946/android
			set = new AnimationSet(true);
			TranslateAnimation translate1 = new TranslateAnimation(50, 0, 200, 0);
			set.addAnimation(translate1);
			TranslateAnimation translate2 = new TranslateAnimation(200, 0, 50, 0);
			translate2.setStartOffset(3000);
			set.addAnimation(translate2);
			set.setDuration(3000);
			mlistview.startAnimation(set);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			translate2.cancel();
			
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
		
	}

}
