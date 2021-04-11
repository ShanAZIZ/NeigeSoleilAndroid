package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnContrat;
    private Button btnReservations;
    private Button btnProfil;
    private Button btnDeconnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.btnContrat = (Button) findViewById(R.id.idContratButton);
        this.btnReservations = (Button) findViewById(R.id.idReservationButton);
        this.btnProfil = (Button) findViewById(R.id.idProfilButton);
        this.btnDeconnexion = (Button) findViewById(R.id.idDeconnexion);
    }
}