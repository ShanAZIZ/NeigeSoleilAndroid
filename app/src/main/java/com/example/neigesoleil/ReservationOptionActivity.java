package com.example.neigesoleil;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.example.neigesoleil.dataservices.DataService;
import com.example.neigesoleil.dataservices.ReservationDataService;
import com.example.neigesoleil.models.Reservation;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ReservationOptionActivity extends AppCompatActivity implements View.OnClickListener {

    private String userId;
    private String reservationId;
    private String contratId;
    private String authToken;
    private Reservation uneReservation;

    private Button btnConfirmerReservation;
    private Button AnnulerReservation;
    private Button modifierReservation;
    private Button retourMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_option);

        this.btnConfirmerReservation = (Button) findViewById(R.id.idConfirmerButton);
        this.AnnulerReservation = (Button) findViewById(R.id.idAnnulerButton);
        this.modifierReservation = (Button) findViewById(R.id.idModifierButton);
        this.retourMenu = (Button) findViewById(R.id.idRetourMenu);

        this.btnConfirmerReservation.setOnClickListener(this);
        this.AnnulerReservation.setOnClickListener(this);
        this.modifierReservation.setOnClickListener(this);
        this.retourMenu.setOnClickListener(this);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.userId = this.getIntent().getStringExtra("id").toString();
        this.contratId = this.getIntent().getStringExtra("contratid").toString();
        this.reservationId = this.getIntent().getStringExtra("reservationid").toString();
        
        DataService.setContext(this);

        ReservationDataService.getReservationDetail(reservationId, authToken, new DataService.StringListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String message) {
                try {
                    JsonNode node = JsonHandler.parse(message);
                    uneReservation = JsonHandler.fromJson(node, Reservation.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void sendRequest(){
        try {
            JsonNode node = JsonHandler.toJson(uneReservation);
            System.out.println(node.toString());
            JSONObject ojb = new JSONObject(node.toString());
            ReservationDataService.sendReservation(ojb, authToken, Request.Method.PUT, new DataService.StringListener() {
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
        if(v.getId() == R.id.idConfirmerButton){
            if(this.uneReservation != null) {
                this.uneReservation.setStatus_reservation("LOCATION");
                this.sendRequest();
            }
        }
        else if(v.getId() == R.id.idModifierButton){
            Intent intent = new Intent(ReservationOptionActivity.this, FormReservationActivity.class);
            intent.putExtra("id", userId);
            intent.putExtra("token", authToken);
            intent.putExtra("reservationid", String.valueOf(this.reservationId));
            intent.putExtra("contratid", String.valueOf(this.contratId));
            ReservationOptionActivity.this.startActivity(intent);
        }
        else if(v.getId() == R.id.idAnnulerButton){
            if(this.uneReservation != null) {
                this.uneReservation.setStatus_reservation("CANCEL");
                this.sendRequest();
            }
        }
        else if(v.getId() == R.id.idRetourMenu) {
            Intent intent = new Intent(DataService.context, ReservationActivity.class);
            intent.putExtra("id", userId);
            intent.putExtra("token", authToken);
            DataService.context.startActivity(intent);
        }
    }
}