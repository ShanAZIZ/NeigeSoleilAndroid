package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.neigesoleil.dataservices.ContratDataService;
import com.example.neigesoleil.dataservices.DataService;
import com.example.neigesoleil.models.Contrat;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ContratDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String idUser;
    private String authToken;
    private String contratId;
    private Contrat unContrat;
    private Boolean isProfile;

    private TextView txtNom;
    private TextView txtDescription;

    private Button retour;
    private Button reserver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrat_detail);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.idUser = this.getIntent().getStringExtra("id").toString();
        this.contratId = this.getIntent().getStringExtra("contrat").toString();
        this.isProfile = this.getIntent().getBooleanExtra("isprofile", false);

        this.txtNom = (TextView) findViewById(R.id.idNomContrat);
        this.txtDescription = (TextView) findViewById(R.id.idDescription);

        this.retour = findViewById(R.id.idRetour);
        this.reserver = findViewById(R.id.idReserver);

        this.retour.setOnClickListener(this);
        this.reserver.setOnClickListener(this);

        DataService.setContext(this);

        ContratDataService.getContrat(contratId, authToken, new DataService.StringListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String message) {
                try {
                    JsonNode node = JsonHandler.parse(message);
                    unContrat = JsonHandler.fromJson(node, Contrat.class);
                    txtNom.setText(unContrat.getNom());
                    txtDescription.setText(unContrat.detailString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.idReserver){
            if (isProfile) {
                Intent intent = new Intent(this, FormReservationActivity.class);
                intent.putExtra("id", idUser);
                intent.putExtra("token", authToken);
                intent.putExtra("contratid", String.valueOf(unContrat.getId()));
                System.out.println(String.valueOf(unContrat.getId()));
                intent.putExtra("reservationid","");
                this.startActivity(intent);

            } else {
                Intent intent = new Intent(this, ProfilActivity.class);
                intent.putExtra("id", idUser);
                intent.putExtra("token", authToken);
                intent.putExtra("isprofile", isProfile);
                this.startActivity(intent);
            }
        }
        else if (v.getId() == R.id.idRetour) {
            Intent intent = new Intent(this, ContratActivity.class);
            intent.putExtra("id", idUser);
            intent.putExtra("token", authToken);
            intent.putExtra("isprofile", isProfile);
            this.startActivity(intent);
        }
    }
}