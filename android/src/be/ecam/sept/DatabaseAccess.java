package be.ecam.sept;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by emino on 24-05-18.
 */


    public class DatabaseAccess {
        private int score;
        private static final String TAG = "MainActivity";
        private FirebaseDatabase database;
        private DatabaseReference myRef;
        private long value;

    DatabaseAccess(){
            this.database = FirebaseDatabase.getInstance();
            this.myRef = database.getReference("message");
            this.value=0;
        }

        public void write(int score){
            this.score = score;
            myRef.setValue(this.score);
        }

        public long read(){
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    value = dataSnapshot.getValue(Long.class);
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
            return value;

        }



}
