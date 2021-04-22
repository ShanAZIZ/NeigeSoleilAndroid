package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neigesoleil.models.Profile;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtAdresse;
    private EditText txtCodePostale;
    private EditText txtVille;
    private EditText txtTelephone;
    private EditText txtRib;
    private Button btnValider;
    private Button btnRetour;

    private String userId;
    private String authToken;

    private Profile currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        txtAdresse = (EditText) findViewById(R.id.idAdresse);
        txtCodePostale = (EditText) findViewById(R.id.idCodePostale);
        txtVille = (EditText) findViewById(R.id.idVille);
        txtTelephone = (EditText) findViewById(R.id.idTelephone);
        txtRib = (EditText) findViewById(R.id.idRib);
        btnValider = (Button) findViewById(R.id.idValiderProfil);
        btnRetour = (Button) findViewById(R.id.idRetourMenu);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.userId = this.getIntent().getStringExtra("id").toString();

        this.btnValider.setOnClickListener(this);
        this.btnRetour.setOnClickListener(this);

        DataService.setContext(this);

        DataService.getProfile(userId, authToken, new DataService.ProfileListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(DataService.context, "Completez votre profile", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {
                try {
                    JsonNode node = JsonHandler.parse(message);
                    currentProfile = JsonHandler.fromJson(node, Profile.class);
                    txtAdresse.setText(currentProfile.getAdresse());
                    txtCodePostale.setText(currentProfile.getCode_postale());
                    txtVille.setText(currentProfile.getVille());
                    txtTelephone.setText(currentProfile.getTelephone());
                    txtRib.setText(currentProfile.getRib());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DataService.context, "Erreur de recuparation du profil", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.idRetourMenu){
            intent = new Intent(this, MenuActivity.class);
            intent.putExtra("token", authToken);
            intent.putExtra("id", userId);
            this.startActivity(intent);
        }
    }
}
