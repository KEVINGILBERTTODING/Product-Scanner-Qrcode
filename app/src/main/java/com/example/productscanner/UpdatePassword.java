package com.example.productscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdatePassword extends AppCompatActivity {
    TextInputEditText et_username, et_password, et_password_old;

    FirebaseDatabase firebaseDtabase = FirebaseDatabase.getInstance();
    DatabaseReference refUsers = firebaseDtabase.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);



        Button btn_update = findViewById(R.id.btn_update_password);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_password_old = findViewById(R.id.et_password_old);

        SharedPreferences sharedPreferences=getSharedPreferences("logindata",MODE_PRIVATE);
        String username=sharedPreferences.getString("useremail",String.valueOf(MODE_PRIVATE));
        et_username.setText(username);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String usernamex = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(UpdatePassword.this, "Username atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernamex)) {
                                String password_old = snapshot.child(usernamex).child("password").getValue(String.class);
                                if (password_old.equals(et_password_old.getText().toString())) {
                                    refUsers.child(usernamex).child("password").setValue(password);
                                    Toast.makeText(UpdatePassword.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UpdatePassword.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(UpdatePassword.this, "Password lama tidak sesuai", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UpdatePassword.this, "Username tidak terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(UpdatePassword.this, "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
