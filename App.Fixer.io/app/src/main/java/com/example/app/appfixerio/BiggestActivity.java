package com.example.app.appfixerio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.appfixerio.models.Exchange;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BiggestActivity extends AppCompatActivity {

    private static final String TAG = "SHOW_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biggest);
        Intent i = getIntent();

        SimpleDateFormat formato =  new SimpleDateFormat("dd-MM-yyyy");

        TextView textRate = (TextView) findViewById(R.id.textRate);
        TextView textDate = (TextView) findViewById(R.id.textDate);

        Gson gS = new Gson();
        Exchange biggest = gS.fromJson(i.getStringExtra("biggest"), Exchange.class);

        textRate.setText(String.format(String.valueOf(biggest.rates.get("BRL"))));
        textDate.setText(String.format(  formato.format(biggest.date)    ));

        Log.e(TAG,"Maior Valor Real: " + biggest.rates.get("BRL"));

        Bundle bundle = i.getExtras();
        Button btHome = (Button) findViewById(R.id.buttonHome);

        btHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                launchActivity();
            }
        }
        );
    }

    private void launchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
