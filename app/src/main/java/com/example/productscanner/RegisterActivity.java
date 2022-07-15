package com.example.productscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    // Membuat object data firebase

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://product-scanner-976a7-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username        = findViewById(R.id.edt_username);
        final EditText  password        = findViewById(R.id.edt_pass);
        final EditText  confPassword    = findViewById(R.id.edt_pass_conf);

        final Button registerBtn     = findViewById(R.id.button_register);
        final TextView loginBtn        = findViewById(R.id.tv_ToLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // mengambil data dari edittext

                final String usernameTxt        = username.getText().toString();
                final String passwordTxt        = password.getText().toString();
                final String confPasswordTxt    = confPassword.getText().toString();

                // cek validasi field username dan password telah terisi atau tidak

                if (usernameTxt.isEmpty() || passwordTxt.isEmpty() || confPasswordTxt.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"Masukkan username dan password", Toast.LENGTH_SHORT).show();
                }

                // cek apakah password dan confirmasi password sesuai

                else if (!passwordTxt.equals(confPasswordTxt)) {

                    Toast.makeText(RegisterActivity.this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
                }

                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // check apakah username sudah terdaftar sebelumnya

                            if (snapshot.hasChild(usernameTxt)) {
                                Toast.makeText(RegisterActivity.this, "Username telah terdaftar", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                // Mengirim data ke firebase realtime database

                                databaseReference.child("users").child(usernameTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(RegisterActivity.this, "Berhasil registrasi.", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Buka Login activity
                finish();
            }
        });
    }
}