package com.vela.app.mobile.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vela.app.mobile.R;
import com.vela.app.mobile.ui.activities.ActivityFragment;
import com.vela.app.mobile.ui.adapter.AccountHistoryAdapter;
import com.vela.app.mobile.ui.eye.EyeFragment;
import com.vela.app.mobile.ui.me.MeFragment;
import com.vela.app.mobile.ui.model.AccountHistoryModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_car);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_activity);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_eye);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_action_person);
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        ImageView Profileimage;
        TextView Name, Carname, Rating, Location, Tvlocation, BtnMapview;
        Button BtnLisview;
        private RecyclerView recyclerView;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
           // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
          //  textView.setText("Work In Progress");

            Profileimage = (ImageView)rootView.findViewById(R.id.profileimage);
            Name = (TextView)rootView.findViewById(R.id.Name);
            Carname=(TextView)rootView.findViewById(R.id.Carname);
            Tvlocation=(TextView)rootView.findViewById(R.id.Tvlocation);
            Rating =(TextView)rootView.findViewById(R.id.Rating);
            Location =(TextView)rootView.findViewById(R.id.Location);

            BtnLisview = (Button) rootView.findViewById(R.id.listview);
            BtnMapview = (TextView) rootView.findViewById(R.id.Tvmapview);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_account_history);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setVisibility(View.VISIBLE);
            getAllNearDrivers();
            return rootView;
        }
        private void getAllNearDrivers() {

            ArrayList<String> list = new ArrayList<String>();

            list.add(0,"http://www.mkyong.com/image/mypic.jpg");
            list.add(1,"Mohammed Ali Khan");
            list.add(2,"Mahindra Renault 7 seater");
            list.add(3,"Mohammed Ali Khan");
            list.add(4,"http://www.mkyong.com/image/mypic.jpg");
            list.add(5,"Mohammed Ali Khan");
            list.add(6,"Mahindra Renault 7 seater");
            list.add(7,"Mohammed Ali Khan");
            list.add(8,"http://www.mkyong.com/image/mypic.jpg");
            list.add(9,"Mohammed Ali Khan");
            list.add(10,"Mahindra Renault 7 seater");
            list.add(11,"Mohammed Ali Khan");
            list.add(12,"http://www.mkyong.com/image/mypic.jpg");
            list.add(13,"Mohammed Ali Khan");
            list.add(14,"Mahindra Renault 7 seater");
            list.add(15,"Mohammed Ali Khan");
            list.add(16,"http://www.mkyong.com/image/mypic.jpg");
            list.add(17,"Mohammed Ali Khan");
            list.add(18,"Mahindra Renault 7 seater");
            list.add(19,"Mohammed Ali Khan");



            Log.e("messages", "messsages list size   " + list.size());

            int Posted_ride = list.size();
//                    totalPointHeaderTv.setText("Give your order from Menucard total Menuitems are: " + Posted_ride);

            ArrayList<AccountHistoryModel> accountHistoryList = new ArrayList<AccountHistoryModel>();

            for (int i = 0; i < list.size(); i++) {

                AccountHistoryModel tempModel = new AccountHistoryModel();

                Object p = list.get(i);

//                        tempModel.setProfileimage("" + "");
                tempModel.setName("" + "Mohammed Ali Khan");
                tempModel.setCarname("" +"Mahindra Renault 7 seater");
                tempModel.setRating("" + "4.2");
                tempModel.setLocation("" + "0.5 km");


                accountHistoryList.add(tempModel);

            }

            Log.e("Home", "Data size for history is " + list.size());
            AccountHistoryAdapter mAdapter = new AccountHistoryAdapter(accountHistoryList);
            recyclerView.setAdapter(mAdapter);
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return PlaceholderFragment.newInstance();
            }
            if (position == 1) {
                return ActivityFragment.newInstance();
            }
            if (position == 2) {
                return EyeFragment.newInstance();
            }
            if (position == 3) {
                return MeFragment.newInstance();
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "";
                case 3:
                    return "";
            }
            return null;
        }
    }


}
