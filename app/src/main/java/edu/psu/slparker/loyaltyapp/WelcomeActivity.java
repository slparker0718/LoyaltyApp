package edu.psu.slparker.loyaltyapp;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class WelcomeActivity extends AppCompatActivity {
    private DrawerLayout DrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        DrawerLayout = findViewById(R.id.drawer_layout);
        displaySelectedScreen(R.id.nav_rewards);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        DrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        displaySelectedScreen(menuItem.getItemId());

                        return true;
                    }
                });
    }

    private void displaySelectedScreen(int navigationId) {
        Fragment fragment = null;

        switch (navigationId) {
            case R.id.nav_my_account:
                fragment = new FragmentAccount();
                break;
            case R.id.nav_coupons:
                fragment = new FragmentCoupons();
                break;
            case R.id.nav_nearby_stores:
                fragment = new FragmentStores();
                break;
            case R.id.nav_rewards:
                fragment = new FragmentRewards();
                break;
        }

        if(fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_welcome, fragment);
            fragmentTransaction.commit();
        }
    }
}
