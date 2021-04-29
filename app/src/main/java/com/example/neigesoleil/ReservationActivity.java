package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.neigesoleil.dataservices.DataService;
import com.example.neigesoleil.dataservices.ReservationDataService;
import com.example.neigesoleil.dataservices.UserDataService;
import com.example.neigesoleil.models.Reservation;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReservationActivity extends AppCompatActivity {

    private ArrayList<Reservation> lesReservations = new ArrayList<>();
    private ListView lvReservations;
    private Button btnRetour;

    private String idUser;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        this.lvReservations = (ListView) findViewById(R.id.idListReservations);
        this.btnRetour = (Button) findViewById(R.id.idRetourMenu);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.idUser = this.getIntent().getStringExtra("id").toString();

        DataService.setContext(this);

        ReservationDataService.getReservationList(idUser, authToken, new UserDataService.StringListener() {
            @Override
            public void onError(String message) {
                
            }

            @Override
            public void onResponse(String message) {
                JsonNode allReservationJson = null;
                try {
                    allReservationJson = JsonHandler.parse(message);
                    Iterator<JsonNode> it = allReservationJson.elements();
                    while(it.hasNext()) {
                        Reservation uneReservation = JsonHandler.fromJson(it.next(), Reservation.class);
                        System.out.println(uneReservation.getId());
                        lesReservations.add(uneReservation);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(DataService.context, android.R.layout.simple_list_item_1, lesReservations);
                    lvReservations.setAdapter(arrayAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        this.btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationActivity.this, MenuActivity.class);
                intent.putExtra("id", idUser);
                intent.putExtra("token", authToken);
                ReservationActivity.this.startActivity(intent);
            }
        });

    }
}