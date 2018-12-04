package edu.psu.slparker.loyaltyapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;

public class WelcomeActivity extends AppCompatActivity {
    private DrawerLayout DrawerLayout;
    private final String TAG = "WELCOME";
    private MessageListener mMessageListener;
    private NotificationBroadcastReceiver notificationBroadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onStart()
    {
        super.onStart();
        registerReceiver(notificationBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(notificationBroadcastReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                String label = new String(message.getContent());

                Log.d(TAG, "Found message: " + label);

                switch (label) {
                    case "coupons":
                        Intent broadcastIntent2 = new Intent(WelcomeActivity.this, NotificationBroadcastReceiver.class);
                        broadcastIntent2.putExtra("ID", 2);
                        broadcastIntent2.putExtra("NOTIFICATION_TYPE", "coupon");
                        sendBroadcast(broadcastIntent2);
                        break;
                    case "nearby":
                        Intent broadcastIntent = new Intent(WelcomeActivity.this, NotificationBroadcastReceiver.class);
                        broadcastIntent.putExtra("ID", 1);
                        broadcastIntent.putExtra("NOTIFICATION_TYPE", "nearbyStore");
                        sendBroadcast(broadcastIntent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onLost(Message message) {
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };

        subscribe();

        DrawerLayout = findViewById(R.id.drawer_layout);

        String notifKey = getIntent().getStringExtra("NOTIF_KEY");
        if (notifKey != null && notifKey.equals("Coupon"))
        {
            displaySelectedScreen(R.id.nav_coupons);
        }
        else if (notifKey != null && notifKey.equals("NearbyStore"))
        {
            displaySelectedScreen(R.id.nav_nearby_stores);
        }
        else
        {
            displaySelectedScreen(R.id.nav_rewards);

        }

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

        intentFilter = new IntentFilter("edu.psu.slparker.loyaltyapp.action.NotificationBroadcastReceiver");
        notificationBroadcastReceiver  = new NotificationBroadcastReceiver();

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

    private void subscribe() {
        Log.i(TAG, "Subscribing.");
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .build();
        Nearby.getMessagesClient(this).subscribe(mMessageListener, options);
    }
}
