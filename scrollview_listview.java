	public static class PlaceholderFragment extends Fragment {
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
		ViewGroup.LayoutParams params = mlistview.getLayoutParams();
		params.height = totalHeight + (mlistview.getDividerHeight() * (mAdapter.getCount() - 1));
		Log.d("myapp","height:"+Integer.toString(params.height));
		//params.height=640;
		/*mlistview.setLayoutParams(params);
		LinearLayout linearList = (LinearLayout)rootView.findViewById(R.id.linear1);
		linearList.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,640));*/

			
			
			return rootView;
		}
/*
  	<ScrollView
  	android:id="@+id/scrollview1"
  	android:layout_height="fill_parent"
  	android:layout_width="fill_parent"
  	android:background="#ff0000">
    
  	     <LinearLayout
        android:id="@+id/linear1"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:orientation="vertical">
        <ListView
            android:id="@+id/listview1"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"></ListView>
        </LinearLayout>
        
    </ScrollView>
*/