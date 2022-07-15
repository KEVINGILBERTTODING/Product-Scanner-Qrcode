package com.example.productscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://product-scanner-976a7-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username     = findViewById(R.id.ti_user_signin);
        final EditText password     = findViewById(R.id.ti_pass_signin);

        final Button loginBtn    =   findViewById(R.id.button_signinSignin);
        final TextView registerBtn =   findViewById(R.id.button_signupSignup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String usernameTxt = username.getText().toString();
                final String passwordTxt = password.getText().toString();

                if (usernameTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Masukkan username dan password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // cek jika username telah terdaftar di firebase database

                            if (snapshot.hasChild(usernameTxt)) {

                                // jika username terdaftar
                                // langsung get password dan sesuaikan password di database dengan yang di input user

                                final String getPassword = snapshot.child(usernameTxt).child("password").getValue(String.class);

                                if (getPassword.equals(passwordTxt)) {

                                    Toast.makeText(LoginActivity.this, "Berhasil login", Toast.LENGTH_SHORT).show();

                                    // Direct ke Main Activity
                                    SharedPreferences sharedPreferences=getSharedPreferences("logindata",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();


                                    editor.putBoolean("logincounter",true);
                                    editor.putString("useremail",usernameTxt);
                                    editor.apply();
                                    finish();


                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

                                }

                                else {

                                    Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else  {

                                Toast.makeText(LoginActivity.this, "Username salah", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Buka Register activity

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });




    }

}