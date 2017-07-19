package com.example.app.appfixerio;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.appfixerio.models.Exchange;
import com.example.app.appfixerio.services.ConsumeService;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SHOW_MESSAGE";
    public  Exchange biggestExchange;
    public  Exchange smallestExchange;

    int element = 1;
    float total = 0, media = 0,auxBiggest = 0, auxSmallest = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConsumeService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /**
         *  Nao e possivel instanciar uma interface, mas com o polimorfismo
         *  ele retorna uma classe q implementa a interface
         */
        ConsumeService service = retrofit.create(ConsumeService.class);

        SimpleDateFormat formato =  new SimpleDateFormat("yyyy-mm-dd");
        Date dt1 = null;
        try {
            dt1 = formato.parse("2011-08-07");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dt2 = null;
        try {
            dt2 = formato.parse("2011-08-09");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime (dt1);
        for (Date dt = dt1; dt.compareTo (dt2) <= 0; ) {
            cal.add (Calendar.DATE, +1);
            dt = cal.getTime();

            Call<Exchange> request = service.listInformation(formato.format(dt),"USD");

            /**
             * O uso do enqueue e de forma assincrona para que a UI nao trave
             */

            request.enqueue(new Callback<Exchange>() {

                @Override
                public void onResponse(Call<Exchange> call, Response<Exchange> response) {
                    if (!response.isSuccessful()){
                        Log.e(TAG,"Message:" +response.code());
                    }else{
                        Exchange informations = response.body();
                        Log.e(TAG,"------------------------");
                        Log.e(TAG,String.format("%s",informations.base));
                        Log.e(TAG,String.format("%s",informations.date));
                        Log.e(TAG,String.format("%s",informations.rates.get("BRL")));
                        TextView textView = (TextView) findViewById(R.id.textMedia);

                        //Calculando o valor medio no periodo
                        total = total + (informations.rates.get("BRL"));
                        media = total/element;
                        textView.setText(String.valueOf(media));
                        element++;


                        //Analisando os maiores e menores valores no periodo
                        if (informations.rates.get("BRL") > auxBiggest  ){
                            biggestExchange = informations;
                            auxBiggest = informations.rates.get("BRL");
                            Log.e(TAG,"Maior valor analisado: " + informations.rates.get("BRL") );
                        }
                        if (informations.rates.get("BRL") < auxSmallest){
                            smallestExchange = informations;
                            auxSmallest = informations.rates.get("BRL");
                            Log.e(TAG,"Menor valor analisado: " + informations.rates.get("BRL") );
                        }


                    }
                }
                @Override
                public void onFailure(Call<Exchange> call, Throwable t) {
                    Log.e(TAG,"Error: "+ t.getMessage());
                }
            });
        }

        ListView listExchange = (ListView) findViewById(R.id.listExchange);
        TextView test = (TextView) findViewById(R.id.textMedia);

        Log.e(TAG,"onCreate");

        Button btBiggest = (Button) findViewById(R.id.buttonBiggest);

        btBiggest.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    launchActivity();
                }
            }
        );
    }

    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    protected void onResume()
    {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    protected  void OnRestart(){
        Log.e(TAG,"onRestart");
    }

    protected  void OnPause(){
        Log.e(TAG,"onPause");
    }

    protected  void OnStop(){
        Log.e(TAG,"onStop");
    }

    private void launchActivity() {
        Intent intent = new Intent(this, BiggestActivity.class);
        Gson gS = new Gson();
        String target = gS.toJson(biggestExchange);
        intent.putExtra("biggest",target);
        this.startActivity(intent);
    }
}
