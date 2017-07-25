package com.example.ktw.mykakao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.ktw.mykakao.R.id.deleteButton;
import static com.example.ktw.mykakao.R.id.sendButton;

public class MainActivity extends AppCompatActivity {

    private final String CHILD_MESSAGE = "messages";

    private ListView mListView;
    private EditText mEditText;
    private Button mSendButton;
    private Button mdeleteButton;
    private boolean isRemoved;


    private DatabaseReference mFirebaseDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        mListView = (ListView) findViewById(R.id.listView);
        mEditText = (EditText) findViewById(R.id.msgText);
        mSendButton = (Button) findViewById(sendButton);
        mdeleteButton = (Button) findViewById(R.id.deleteButton);


//        String [] samples = {
//                "text1",
//                "text2",
//                "text3"
//        };
        final ArrayList<String> samples = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, samples);
        mListView.setAdapter(adapter);


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message("kidesk", mEditText.getText().toString());
                mFirebaseDatabaseReference.child(CHILD_MESSAGE).push().setValue(message);
                mEditText.setText("");
            }
        });

        mdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRemoved = false)
                {
                    mFirebaseDatabaseReference.child(CHILD_MESSAGE).getParent().setValue(null);
                }
            }
        });


        ChildEventListener childEventListener = new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                adapter.add(message.getText());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
                // 이걸 처음 해본 겁니다.
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
FirebaseDatabase.getInstance().getReference().child(CHILD_MESSAGE).
        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
        child(CHILD_MESSAGE).orderByChild("text").
        addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        dataSnapshot.getRef().removeValue();
        //이게 두번째 해본 겁니다.
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        mFirebaseDatabaseReference.child(CHILD_MESSAGE).addChildEventListener(childEventListener);



    }
}
