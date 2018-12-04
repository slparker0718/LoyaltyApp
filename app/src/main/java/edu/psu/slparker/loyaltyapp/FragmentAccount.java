package edu.psu.slparker.loyaltyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.constraint.Constraints.TAG;

public class FragmentAccount extends Fragment {
    private FirebaseAuth firebaseAuth;
    private TextView textView_userFirstName;
    private TextView textView_userLastName;
    private TextView textView_userEmailAddress;
    private TextView textView_userUsername;
    private DatabaseReference mDatabase;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Account");

        textView_userFirstName = getView().findViewById(R.id.textView_userFirstName);
        textView_userLastName = getView().findViewById(R.id.textView_userLastName);
        textView_userUsername = getView().findViewById(R.id.textView_userUsername);
        textView_userEmailAddress = getView().findViewById(R.id.textView_userEmailAddress);

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                textView_userFirstName.setText(user.getFirstName());
                textView_userLastName.setText(user.getLastName());
                textView_userUsername.setText(user.getusername());
                textView_userEmailAddress.setText(user.getEmailAddress());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("users").child(uid).addValueEventListener(listener);
    }
}
