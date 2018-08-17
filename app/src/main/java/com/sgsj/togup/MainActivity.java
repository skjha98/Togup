package com.sgsj.togup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mainDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mainDrawerToggle;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.drawer_menu_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container, new DashboardFragment()).commit();
                break;
            case R.id.drawer_menu_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container, new ProfileFragment()).commit();
                break;
            case R.id.drawer_menu_withdraw:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container, new AboutFragment()).commit();
                break;
        }
        mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,getString(R.string.adMobID));

        mainDrawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        mainDrawerToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close);
        mainDrawerLayout.addDrawerListener(mainDrawerToggle);
        mainDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_container, new DashboardFragment()).commit();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.drawer_menu_dashboard);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mainDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
