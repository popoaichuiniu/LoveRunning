package com.popoaichuiniu.jacy.myapplication;



import android.content.Intent;
import android.graphics.Bitmap;

import android.support.v7.app.AppCompatActivity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

import com.popoaichuiniu.jacy.R;
import com.popoaichuiniu.jacy.tools.BitmapHandle;
import com.popoaichuiniu.jacy.tools.GetScreenSize;
import com.popoaichuiniu.jacy.views.RegisterDialog;

public class IntroduceActivity extends AppCompatActivity implements View.OnClickListener {

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
    private  Button  introduceRegister;
    private  Button  introduceLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_introduce);
        introduceRegister= (Button) findViewById(R.id.introduce_register);
        introduceLogin= (Button) findViewById(R.id.introduce_login);
        introduceRegister.setOnClickListener(this);
        introduceLogin.setOnClickListener(this);





        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

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

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView =  inflater.inflate(R.layout.fragment_introduce, container, false);
            ImageView imageView= (ImageView) rootView.findViewById(R.id.introduce_image);
            Bitmap bitmap=null;
            GetScreenSize getScreenSize=new GetScreenSize(getContext());
            int widthScreen = getScreenSize.getScreenWidth();
            int heightScreen = getScreenSize.getScreenHeight();
            switch (getArguments().getInt(ARG_SECTION_NUMBER))
            {
                case 0:
                    bitmap=BitmapHandle.changeBitmap(getContext(), R.drawable.first_introduce,widthScreen, heightScreen);
                    imageView.setImageBitmap(bitmap);

                    break;
                case 1:
               bitmap=BitmapHandle.changeBitmap(getContext(), R.drawable.second_introduce,widthScreen, heightScreen);
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2:
                   bitmap=BitmapHandle.changeBitmap(getContext(), R.drawable.third_introduce,widthScreen, heightScreen);
                    imageView.setImageBitmap(bitmap);
                    break;
                default:
                    Toast.makeText(getContext(),"hewuhe",Toast.LENGTH_SHORT).show();

            }
            return rootView;
        }

        /**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
         * one of the sections/tabs/pages.
         */
    }
        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                return PlaceholderFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 3;
            }


        }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.introduce_register:
                RegisterDialog registerDialog=new RegisterDialog(this,R.style.MyDialog);
                registerDialog.show();
                break;
            case R.id.introduce_login:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
