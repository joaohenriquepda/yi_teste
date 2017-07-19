package com.example.app.appfixerio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.appfixerio.models.Exchange;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by joao on 19/07/17.
 */

public class SmallestActivity extends AppCompatActivity {

    private static final String TAG = "SHOW_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smallest);

        TextView textRate = (TextView) findViewById(R.id.textRate);
        TextView textDate = (TextView) findViewById(R.id.textDate);


        Intent i = getIntent();

        SimpleDateFormat formato =  new SimpleDateFormat("dd-MM-yyyy");

        Gson gS = new Gson();
        Exchange smallest = gS.fromJson(i.getStringExtra("smallest"), Exchange.class);

        textRate.setText(String.format(String.valueOf(smallest.rates.get("BRL"))));
        textDate.setText(String.format(  formato.format(smallest.date)    ));

        Log.e(TAG,"Menor Valor Real: " + smallest.rates.get("BRL"));


        Button btHome = (Button) findViewById(R.id.buttonHome);

        btHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                launchActivity();
            }
        });
    }

    private void launchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
