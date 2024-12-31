package com.rasya.uts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private TextInputEditText inputEmail, inputUser, inputPassword;
    private MaterialButton btnRegister;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // XML Anda

        // Inisialisasi Views
        inputEmail = findViewById(R.id.inputEmail);
        inputUser = findViewById(R.id.inputUser);
        inputPassword = findViewById(R.id.inputPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this);
        mFirestore = FirebaseFirestore.getInstance();

        // Logika Tombol Register
        btnRegister.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String username = inputUser.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(email, username, password);
            }
        });
    }

    // Metode untuk menyimpan data pengguna ke Firestore
    private void registerUser(String email, String username, String password) {
        // Membuat struktur data
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("username", username);
        user.put("password", password);

        // Menyimpan data ke koleksi "pengguna"
        mFirestore.collection("pengguna")
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(Register.this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();

                    // Pindah ke halaman login setelah registrasi berhasil
                    Intent loginIntent = new Intent(Register.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Register.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
