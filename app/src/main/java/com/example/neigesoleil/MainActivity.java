package com.example.neigesoleil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neigesoleil.dataservices.DataService;
import com.example.neigesoleil.dataservices.UserDataService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnConnexion;
    private EditText txtUsername;
    private EditText txtPassword;
    private static String authToken;
    private static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnConnexion = (Button) findViewById(R.id.idConnexion);
        this.txtUsername = (EditText) findViewById(R.id.idUsername);
        this.txtPassword = (EditText) findViewById(R.id.idPassword);

        this.btnConnexion.setOnClickListener(this);

        DataService.setContext(this);
    }

    public static String getAuthToken() {
        return MainActivity.authToken;
    }

    public static void setAuthToken(String authToken) {
        MainActivity.authToken = authToken;
    }

    public String getUserId() {
        return MainActivity.userId;
    }

    public static void setUserId(String userId) {
        MainActivity.userId = userId;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.idConnexion) {
            String username = this.txtUsername.getText().toString();
            String password = this.txtPassword.getText().toString();

            if(username.equals("") || password.equals("")){
                Toast.makeText(this, "Veuillez tout remplir les champs", Toast.LENGTH_LONG).show();
            }
            else {
                UserDataService.getToken(username, password, new DataService.StringListener() {
                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String bienvenue) {
                        System.out.println(userId + bienvenue);
                        if(!bienvenue.equals("")) {
                            Intent unIntent = new Intent(MainActivity.this, MenuActivity.class);
                            Toast.makeText(MainActivity.this, bienvenue, Toast.LENGTH_SHORT).show();
                            unIntent.putExtra("token", authToken);
                            unIntent.putExtra("id", userId);
                            MainActivity.this.startActivity(unIntent);
                        }
                    }
                });
            }
        }
    }
}