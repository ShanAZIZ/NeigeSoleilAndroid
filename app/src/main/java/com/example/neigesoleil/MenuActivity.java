package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.neigesoleil.dataservices.DataService;
import com.example.neigesoleil.dataservices.UserDataService;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnContrat;
    private Button btnReservations;
    private Button btnProfil;
    private Button btnDeconnexion;
    private String authToken;
    private String userId;
    private Boolean isProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.btnContrat = (Button) findViewById(R.id.idContratButton);
        this.btnReservations = (Button) findViewById(R.id.idReservationButton);
        this.btnProfil = (Button) findViewById(R.id.idProfilButton);
        this.btnDeconnexion = (Button) findViewById(R.id.idDeconnexion);

        this.btnContrat.setOnClickListener(this);
        this.btnReservations.setOnClickListener(this);
        this.btnProfil.setOnClickListener(this);
        this.btnDeconnexion.setOnClickListener(this);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.userId = this.getIntent().getStringExtra("id").toString();

        DataService.setContext(this);
        checkProfileComplete();

    }

    // Verifie si un utilisateur a un profil complet
    public void checkProfileComplete(){
        UserDataService.getProfile(userId, authToken, new UserDataService.StringListener() {
            @Override
            public void onError(String message) {
                isProfile = false;
                System.out.println(message);
            }

            @Override
            public void onResponse(String message) {
                System.out.println(message);
                isProfile = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.idContratButton){
            intent = new Intent(this, ContratActivity.class);
            intent.putExtra("token", authToken);
            intent.putExtra("id", userId);
            intent.putExtra("isprofile", isProfile);
            this.startActivity(intent);
        }
        else if (v.getId() == R.id.idReservationButton){
            if(isProfile) {
                intent = new Intent(this, ReservationActivity.class);
            } else {
                intent = new Intent(this, ProfilActivity.class);
            }
            intent.putExtra("token", authToken);
            intent.putExtra("id", userId);
            this.startActivity(intent);
        }
        else if (v.getId() == R.id.idProfilButton){
            intent = new Intent(this, ProfilActivity.class);
            intent.putExtra("token", authToken);
            intent.putExtra("id", userId);
            intent.putExtra("isprofile", isProfile);
            this.startActivity(intent);
        }

        else if (v.getId() == R.id.idDeconnexion){
            authToken = "";
            intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }

    }
}