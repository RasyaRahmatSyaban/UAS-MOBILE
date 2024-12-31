package com.rasya.uts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText inputUsername, inputPassword;
    private MaterialButton btnLogin, btnRegister;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sesuai dengan XML Anda

        // Inisialisasi Views
        inputUsername = findViewById(R.id.inputUser);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();

        // Logika Tombol Login
        btnLogin.setOnClickListener(v -> {
            String username = inputUsername.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                checkUserCredentials(username, password);
            }
        });

        // Logika Tombol Register
        btnRegister.setOnClickListener(v -> {
            Intent registerIntent = new Intent(MainActivity.this, Register.class);
            startActivity(registerIntent);
        });
    }

    // Metode untuk mengecek kredensial pengguna di Firestore
    private void checkUserCredentials(String username, String password) {
        mFirestore.collection("pengguna")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password) // Menggunakan password sementara di field Stambuk
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Jika pengguna ditemukan
                            Toast.makeText(MainActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

                            // Arahkan ke halaman Home
                            Intent homeIntent = new Intent(MainActivity.this, Home.class);
                            homeIntent.putExtra("USERNAME_KEY", username);
                            startActivity(homeIntent);
                            finish();
                        }
                    } else {
                        // Jika pengguna tidak ditemukan
                        Toast.makeText(MainActivity.this, "Login Gagal! Periksa kembali Username dan Password", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
