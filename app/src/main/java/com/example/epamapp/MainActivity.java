package com.example.epamapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.epamapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    private TextView mFbTextView;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRootChild = mDatabaseReference.child("text");



    Button next;
    Button next2;
    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());








        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });


        next = (Button) findViewById(R.id.btnNext);
        next2 = (Button) findViewById(R.id.btnNext2);






        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent siguiente = new Intent(MainActivity.this, MainActivity2.class);
              startActivity(siguiente);
            }
        });


        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente2 = new Intent(MainActivity.this, Categories.class);
                startActivity(siguiente2);
            }
        });







        mFbTextView = (TextView) findViewById(R.id.fbTextView);



    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else{
            String email = firebaseUser.getEmail();
            binding.emailTv.setText(email);

        }
    }







    @Override
    protected void onStart() {
        super.onStart();

        mRootChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String text = snapshot.getValue().toString();
                mFbTextView.setText(text);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }




















}