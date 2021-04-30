package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormReservationActivity extends AppCompatActivity implements View.OnClickListener {

    private String userId;
    private String authToken;
    private String contratId;
    private String reservationId;

    private EditText dateDebut;
    private EditText dateFin;
    private Button btnValider;
    private Button btnAnnuler;

    private Boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_reservation);

        this.authToken = this.getIntent().getStringExtra("token").toString();
        this.userId = this.getIntent().getStringExtra("id").toString();
        this.contratId = this.getIntent().getStringExtra("contratid").toString();
        this.reservationId = this.getIntent().getStringExtra("reservationid").toString();

        if (!this.reservationId.equals("")){
            // TODO: Get reservation info
            edit = true;
        }

        this.dateDebut = findViewById(R.id.idDateDebut);
        this.dateFin = findViewById(R.id.idDateFin);
        this.btnValider = findViewById(R.id.idValiderReservation);
        this.btnAnnuler = findViewById(R.id.idAnnuler);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.idReserver) {

        }
        else if (v.getId() == R.id.idRetour) {

        }
    }
}