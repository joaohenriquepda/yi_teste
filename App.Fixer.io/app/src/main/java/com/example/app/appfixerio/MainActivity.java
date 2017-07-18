package com.example.app.appfixerio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.app.appfixerio.models.Exchange;
import com.example.app.appfixerio.models.FixerInformations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SHOW_MESSAGE";

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
        SimpleDateFormat formato =  new SimpleDateFormat("YYYY-MM-dd");
        Date data1 = new Date(System.currentTimeMillis());
        Date data2 = new Date(System.currentTimeMillis());

        String name = formato.format(data1);
        Log.e(TAG,name);


        ConsumeService service = retrofit.create(ConsumeService.class);

        Date date = new Date(System.currentTimeMillis());
//        Log.e(TAG, String.format("%s",date));
       Call<Exchange> request = service.listInformation("2017-07-17","USD");

        /**
         * O uso do enqueue e de forma assincrona para que a UI nao trave
         */
        request.enqueue(new Callback<Exchange>() {
            @Override
            public void onResponse(Call<Exchange> call, Response<Exchange> response) {
                if (!response.isSuccessful()){
                    Log.e(TAG,"Message:" +response.code());
                }else{
                    Log.e(TAG,"AQui");
                    Exchange informations = response.body();
                    Log.e(TAG,"------------------------");
                    Log.e(TAG,String.format("%s",informations.base));
                    Log.e(TAG,String.format("%s",informations.date));
                    Log.e(TAG,String.format("%s",informations.rates.get("BRL")));

                    // for (Exchange exc: informations){
                    //     Log.e(TAG,String.format("$s",exc.date));
                    // }

                }

            }

            @Override
            public void onFailure(Call<Exchange> call, Throwable t) {
                Log.e(TAG,"Error: "+ t.getMessage());
            }
        });
    }
}
