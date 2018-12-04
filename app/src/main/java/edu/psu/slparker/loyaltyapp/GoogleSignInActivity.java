package edu.psu.slparker.loyaltyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GoogleSignInActivity extends AppCompatActivity {

    private EditText editText_GooglefirstName;
    private EditText editText_GooglelastName;
    private EditText editText_GoogleuserName;

    private Button button_GooglesignUpSubmit;
    private FirebaseAuth firebaseAuth;
    private final String TAG = "SignUpActivity";
    private DatabaseReference mDatabase;
    private String email_address;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);


        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
         uid = intent.getStringExtra("UID");
         email_address = intent.getStringExtra("EMAIL_ADDRESS");

        editText_GooglefirstName = findViewById(R.id.editText_GoogleFirstName);
        editText_GooglelastName = findViewById(R.id.editText_GoogleLastName);
        editText_GoogleuserName = findViewById(R.id.editText_GoogleUsername);
        button_GooglesignUpSubmit = findViewById(R.id.button_GoogleSignUpSubmit);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        button_GooglesignUpSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                storeUserInfo();
                Intent intent = new Intent(GoogleSignInActivity.this, WelcomeActivity.class );
                startActivity(intent);
            }
        });
    }

    private void storeUserInfo() {
        User user = new User(editText_GooglefirstName.getText().toString(),
                editText_GooglelastName.getText().toString(),
                editText_GoogleuserName.getText().toString(),
                email_address,
                0,
                0,
                new ArrayList<String>());

        mDatabase.child("users").child(uid).setValue(user);
    }
}

