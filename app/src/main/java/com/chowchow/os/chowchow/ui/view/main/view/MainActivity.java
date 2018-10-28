package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.chowchow.os.chowchow.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        ImageView imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            FrameLayout chowchow = findViewById(R.id.frame_app);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    chowchow.setVisibility(View.VISIBLE);
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_schedule:
                    //mTextMessage.setText(R.string.title_schedule);
                    chowchow.setVisibility(View.VISIBLE);
                    fragment = new ScheduleFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_tour:
                    //mTextMessage.setText(R.string.title_tour);
                    chowchow.setVisibility(View.VISIBLE);
                    fragment = new SuggestTourFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_map:
                    //mTextMessage.setText(R.string.title_map);
                    chowchow.setVisibility(View.VISIBLE);
                    fragment = new MapFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_account:
                    //mTextMessage.setText(R.string.title_account);
                    chowchow.setVisibility(View.GONE);
                    fragment = new LoginFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
