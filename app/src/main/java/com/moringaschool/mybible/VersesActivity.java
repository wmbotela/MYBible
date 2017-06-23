package com.moringaschool.mybible;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VersesActivity extends AppCompatActivity{
    @Bind(R.id.listView) ListView mListView;


    //    Emergency calls
    private String[] ecalls = new String[]{"Upset", "Lonely", "Need Direction", "Worried", "Anxious", "Unhappy", "In Danger", "Depressed", "Lack Faith",
            "Discouraged with Work", "Seeking Peace", "Leaving on a trip", "Struggling with Loss", "Struggling Financially"};

    private DatabaseReference mDatabase;
    private List<String> scriptures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        

        scriptures= new ArrayList<>();



        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.custom_list_item, ecalls);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ecalls = ((TextView) view).getText().toString();
//                Toast.makeText(VersesActivity.this, ecalls, Toast.LENGTH_LONG).show();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Scriptures");
                final Integer itemPosition = i;
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            String scripture = snapshot.getValue(String.class);
                            scriptures.add(scripture);
                        }
                        AlertDialog.Builder scripturedialogue = new AlertDialog.Builder(VersesActivity.this);
                        scripturedialogue.setMessage(scriptures.get(itemPosition));
                        scripturedialogue.show();
                        scripturedialogue.setCancelable(true);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



