package edu.psu.slparker.loyaltyapp;

import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//firebase and google
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    public Button button_login;
    public Button button_createAccount;
    public SignInButton button_googleSignIn;
    public FirebaseAuth firebaseAuth;
    public GoogleSignInClient googleSignInClient;
    private EditText editText_loginEmailAddress;
    private EditText editText_loginPassword;
    private static int RC_SIGN_IN = 100;
    private String TAG = "MainActivity";
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
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        button_login = findViewById(R.id.button_login);
        button_createAccount = findViewById(R.id.button_CreateAccount);
        button_googleSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        editText_loginEmailAddress = findViewById(R.id.editText_loginEmailAddress);
        editText_loginPassword = findViewById(R.id.editText_loginPassword);

        //Configure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });

        button_createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        button_googleSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signInWithGoogle();
            }
        });


        // START: Move this logic to activity wherever we check for beacon signal
        // NOTE: There are two methods above, onStart and onStop, we want to move these to the activity the beacon check is in as well.
        intentFilter = new IntentFilter("edu.psu.slparker.loyaltyapp.action.NotificationBroadcastReceiver");
        notificationBroadcastReceiver  = new NotificationBroadcastReceiver();

        Intent broadcastIntent = new Intent(MainActivity.this, NotificationBroadcastReceiver.class);
        broadcastIntent.putExtra("ID", 1);
        broadcastIntent.putExtra("NOTIFICATION_TYPE", "nearbyStore");
        sendBroadcast(broadcastIntent);

        Intent broadcastIntent2 = new Intent(MainActivity.this, NotificationBroadcastReceiver.class);
        broadcastIntent2.putExtra("ID", 2);
        broadcastIntent2.putExtra("NOTIFICATION_TYPE", "coupon");
        sendBroadcast(broadcastIntent2);
        // END
    }

    private void signInWithGoogle()
    {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);

            // Signed in successfully, show authenticated UI.
            //startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }

    private void authenticateUser()
    {
        firebaseAuth.signInWithEmailAndPassword(editText_loginEmailAddress.getText().toString(), editText_loginPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), getString(R.string.toast_loginFailure), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
