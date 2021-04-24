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

import org.json.JSONException;
import org.json.JSONObject;

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
    private Boolean isProfile;

    private Profile currentProfile = new Profile();

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
        this.isProfile = this.getIntent().getBooleanExtra("isprofile", false);

        this.btnValider.setOnClickListener(this);
        this.btnRetour.setOnClickListener(this);

        DataService.setContext(this);

        DataService.getProfile(userId, authToken, new DataService.StringListener() {
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

    public void sendProfile(Profile currentProfile){
        JsonNode node = JsonHandler.toJson(currentProfile);
        try {
            JSONObject jObject = new JSONObject(node.toString());
            DataService.setProfile(jObject, isProfile, authToken, new DataService.StringListener(){
                @Override
                public void onError(String message) {
                    Toast.makeText(DataService.context, message, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onResponse(String message) {
                    Intent intent = new Intent(DataService.context, MenuActivity.class);
                    intent.putExtra("token", authToken);
                    intent.putExtra("id", userId);
                    ProfilActivity.this.startActivity(intent);
                    Toast.makeText(DataService.context, message, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(DataService.context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.idRetourMenu){
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("token", authToken);
            intent.putExtra("id", userId);
            this.startActivity(intent);
        }
        else if(v.getId() == R.id.idValiderProfil){
            currentProfile.setAdresse(txtAdresse.getText().toString());
            currentProfile.setCode_postale(txtCodePostale.getText().toString());
            currentProfile.setVille(txtVille.getText().toString());
            currentProfile.setTelephone(txtTelephone.getText().toString());
            currentProfile.setRib(txtRib.getText().toString());
            currentProfile.setUser(Integer.parseInt(this.userId));
            sendProfile(currentProfile);
        }
    }
}
