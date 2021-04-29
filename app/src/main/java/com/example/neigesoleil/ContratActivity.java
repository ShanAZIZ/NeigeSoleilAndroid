package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.neigesoleil.dataservices.ContratDataService;
import com.example.neigesoleil.dataservices.DataService;
import com.example.neigesoleil.models.Contrat;
import com.example.neigesoleil.models.Reservation;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ContratActivity extends AppCompatActivity {

    private ArrayList<Contrat> lesContrats = new ArrayList<>();
    private ListView lvContrats;
    private Button btnRetour;

    private String idUser;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrat);

        this.lvContrats = (ListView) findViewById(R.id.idListContrats);
        this.btnRetour = (Button) findViewById(R.id.idRetourMenu);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.idUser = this.getIntent().getStringExtra("id").toString();

        DataService.setContext(this);

        ContratDataService.getContratList(idUser, authToken, new DataService.StringListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String message) {
                JsonNode allContratJson = null;
                try {
                    allContratJson = JsonHandler.parse(message);
                    Iterator<JsonNode> it = allContratJson.elements();
                    while(it.hasNext()) {
                        Contrat unContrat = JsonHandler.fromJson(it.next(), Contrat.class);
                        lesContrats.add(unContrat);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(DataService.context, android.R.layout.simple_list_item_1, lesContrats);
                    lvContrats.setAdapter(arrayAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        this.btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContratActivity.this, MenuActivity.class);
                intent.putExtra("id", idUser);
                intent.putExtra("token", authToken);
                ContratActivity.this.startActivity(intent);
            }
        });
    }
}