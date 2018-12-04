package edu.psu.slparker.loyaltyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FragmentRewards extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private User user;
    private int loyaltyPoints;
    private int punchCardCount = 0;
    private List<String> loyaltyFeed = new ArrayList<>();
    private TextView textView_loyaltyPoints;
    private GridViewAdapter gridViewAdapter;
    private GridView gridView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridViewAdapter = new GridViewAdapter();
        gridViewAdapter.mContext = this.getContext();
        gridViewAdapter.filledStars = punchCardCount;
        gridView.setAdapter(gridViewAdapter);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_loyaltyFeed);
        mAdapter = new LoyaltyFeedAdapter(this.getContext(), loyaltyFeed);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_loyaltyPoints = (TextView) getView().findViewById(R.id.textView_loyaltyPoints);

        getActivity().setTitle("Rewards");

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                loyaltyPoints = user.getLoyaltyPoints();
                punchCardCount = user.getPunchCardCount();
                List<String> feed = user.getLoyaltyFeed();

                textView_loyaltyPoints.setText(Integer.toString(loyaltyPoints));
                gridViewAdapter.filledStars = punchCardCount;
                gridView.setAdapter(gridViewAdapter);

                loyaltyFeed.clear();
                loyaltyFeed.addAll(feed);
                mAdapter.notifyDataSetChanged();
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
