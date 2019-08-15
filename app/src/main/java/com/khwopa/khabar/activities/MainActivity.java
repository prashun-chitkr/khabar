package com.khwopa.khabar.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.khwopa.khabar.R;
import com.khwopa.khabar.fragments.PopularFragment;
import com.khwopa.khabar.fragments.SportsFragment;
import com.khwopa.khabar.fragments.TechFragment;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        BottomNavigationView.OnNavigationItemSelectedListener{

    private SwipeRefreshLayout refreshLayout;
    private BottomNavigationView bottomNavView;
    private Fragment popular = new PopularFragment();
    private Fragment sports = new SportsFragment();
    private Fragment tech = new TechFragment();
    private FragmentManager fm = getSupportFragmentManager();
    private Fragment active = popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.swipeRefresh);
        bottomNavView = findViewById(R.id.bottomNavBar);

        refreshLayout.setRefreshing(true);

        fm.beginTransaction().add(R.id.container, tech, "tech").hide(tech).commit();
        fm.beginTransaction().add(R.id.container, sports, "sports").hide(sports).commit();
        fm.beginTransaction().add(R.id.container, popular, "popular").commit();

        bottomNavView.setOnNavigationItemSelectedListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                fm.beginTransaction().detach(active).attach(active).commit();
                refreshLayout.setRefreshing(false);
            }
        }, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.popular:
                fm.beginTransaction().hide(active).show(popular).commit();
                active = popular;
                return true;

            case R.id.sports:
                fm.beginTransaction().hide(active).show(sports).commit();
                active = sports;
                return true;

            case R.id.tech:
                fm.beginTransaction().hide(active).show(tech).commit();
                active = tech;
                return true;
        }
        return false;
    }
}
