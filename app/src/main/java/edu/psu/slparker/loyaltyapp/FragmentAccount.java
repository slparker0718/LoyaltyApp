package edu.psu.slparker.loyaltyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentAccount extends Fragment {
    private FirebaseAuth firebaseAuth;
    private TextView textView_userEmailAddress;
    private TextView textView_welcomeUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        getActivity().setTitle("Account");
        textView_userEmailAddress = view.findViewById(R.id.textView_userEmailAddress);
        textView_welcomeUser = view.findViewById(R.id.textView_welcomeUser);
        setUserEmailAddress();
        setUserDisplayName();
    }

    private void setUserEmailAddress()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textView_userEmailAddress.setText(user.getEmail());
    }

    private void setUserDisplayName()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String welcome = textView_welcomeUser.getText().toString();
        StringBuilder stringBuilder = new StringBuilder(30);
        stringBuilder.append(welcome);
        stringBuilder.append(", " + user.getDisplayName() + "!");
        textView_welcomeUser.setText(stringBuilder.toString());
    }
}
