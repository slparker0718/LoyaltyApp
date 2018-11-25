package edu.psu.slparker.loyaltyapp;

//android
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//firebase
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    private EditText editText_firstName;
    private EditText editText_lastName;
    private EditText editText_userName;
    private EditText editText_password;
    private EditText editText_emailAddress;
    private Button button_signUpSubmit;
    private FirebaseAuth firebaseAuth;
    private final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();

        editText_firstName = findViewById(R.id.editText_FirstName);
        editText_lastName = findViewById(R.id.editText_LastName);
        editText_userName = findViewById(R.id.editText_Username);
        editText_password = findViewById(R.id.editText_Password);
        editText_emailAddress = findViewById(R.id.editText_EmailAddress);
        button_signUpSubmit = findViewById(R.id.button_SignUpSubmit);

        button_signUpSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddUserToFirebase();
            }
        });
    }

    private void AddUserToFirebase()
    {
        firebaseAuth.createUserWithEmailAndPassword(editText_emailAddress.getText().toString(), editText_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(editText_firstName.getText().toString() + " " + editText_lastName.getText().toString()).build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(SignUpActivity.this, WelcomeActivity.class));
                                                Toast.makeText(getApplicationContext(), getString(R.string.toast_signUpSuccess), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.toast_signUpFailed),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
