package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnConnexion;
    private TextView txtUsername;
    private TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnConnexion = (Button) findViewById(R.id.idConnexion);
        this.txtUsername = (TextView) findViewById(R.id.idUsername);
        this.txtPassword = (TextView) findViewById(R.id.idPassword);
    }
}