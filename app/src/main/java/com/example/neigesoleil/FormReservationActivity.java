package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.neigesoleil.dataservices.DataService;
import com.example.neigesoleil.dataservices.ReservationDataService;
import com.example.neigesoleil.models.Reservation;
import com.fasterxml.jackson.databind.JsonNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FormReservationActivity extends AppCompatActivity implements View.OnClickListener {

    private String userId;
    private String authToken;
    private String contratId;
    private String reservationId;

    private EditText dateDebut;
    private EditText dateFin;
    private Button btnValider;
    private Button btnAnnuler;

    private Reservation uneReservation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reservation);

        DataService.setContext(this);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.userId = this.getIntent().getStringExtra("id").toString();
        this.contratId = this.getIntent().getStringExtra("contratid").toString();
        this.reservationId = this.getIntent().getStringExtra("reservationid").toString();

        this.dateDebut = findViewById(R.id.idDateDebut);
        this.dateFin = findViewById(R.id.idDateFin);
        this.btnValider = findViewById(R.id.idValiderReservation);
        this.btnAnnuler = findViewById(R.id.idAnnuler);

        if (!this.reservationId.equals("")){
            ReservationDataService.getReservationDetail(reservationId, authToken, new DataService.StringListener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(String message) {
                    try {
                        JsonNode node = JsonHandler.parse(message);
                        uneReservation = JsonHandler.fromJson(node, Reservation.class);
                        dateDebut.setText(uneReservation.getDate_debut_sejour());
                        dateFin.setText(uneReservation.getDate_fin_sejour());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        this.btnValider.setOnClickListener(this);
        this.btnAnnuler.setOnClickListener(this);

    }

    public void sendRequest(int method) {
        try {
            JsonNode node = JsonHandler.toJson(uneReservation);
            System.out.println(node.toString());
            JSONObject ojb = new JSONObject(node.toString());
            ReservationDataService.sendReservation(ojb, authToken, method, new DataService.StringListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(DataService.context, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String message) {
                    Toast.makeText(DataService.context, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DataService.context, ReservationActivity.class);
                    intent.putExtra("id", userId);
                    intent.putExtra("token", authToken);
                    DataService.context.startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(DataService.context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.idValiderReservation) {
            System.out.println(dateDebut.getText().toString());
            System.out.println(dateFin.getText().toString());
            if(uneReservation == null){
                uneReservation = new Reservation(
                        Integer.parseInt(userId),
                        Integer.parseInt(contratId),
                        dateDebut.getText().toString(),
                        dateFin.getText().toString()
                        );
                sendRequest(Request.Method.POST);
            }
            else {
                uneReservation.setDate_debut_sejour(dateDebut.getText().toString());
                uneReservation.setDate_fin_sejour(dateFin.getText().toString());
                sendRequest(Request.Method.PUT);
            }
        }
        else if (v.getId() == R.id.idAnnuler) {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("id", userId);
            intent.putExtra("token", authToken);
            this.startActivity(intent);
        }
    }
}