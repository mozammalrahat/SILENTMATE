package com.example.automaticphonesilencer;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MySavedLocations extends AppCompatActivity {

    ListView listView;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_saved_locations);

        listView = (ListView)findViewById(R.id.listViewId);
        homeButton = (Button)findViewById(R.id.homeid);


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MySavedLocations.this,MainActivity.class));
                finish();
            }
        });







        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Places");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    LocationScan locationScan =snapshot.getValue(LocationScan.class);
                   String txt = snapshot.getKey();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                SparseBooleanArray positionchecker = listView.getCheckedItemPositions();
//                int count = listView.getCount();
//                for (int item =count-1;item>=0;item-- ){
//
//                    if(positionchecker.get(item)){
//
//                        String name = list.get(item).toString();
//                        adapter.remove(list.get(item));
//                        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("ProgrammingKnowledge").child(name);
//                        reference.removeValue();
//                    }
//
//                }
//                positionchecker.clear();
//                adapter.notifyDataSetChanged();
//                return false;
//            }
//        });



    }
}
