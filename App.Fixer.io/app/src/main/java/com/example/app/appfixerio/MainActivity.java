package com.example.app.appfixerio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.app.appfixerio.models.Exchange;
import com.example.app.appfixerio.models.FixerInformations;

import java.text.SimpleDateFormat;
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
        ConsumeService service = retrofit.create(ConsumeService.class);
        SimpleDateFormat formato =  new SimpleDateFormat("YYYY-MM-DD");
        Date date = new Date(System.currentTimeMillis());
        Log.e(TAG, String.format("%s",date));
        Call<FixerInformations> request = service.listInformation(formato.format(date),"USD");

        /**
         * O uso do enqueue e de forma assincrona para que a UI nao trave
         */
        request.enqueue(new Callback<FixerInformations>() {
            @Override
            public void onResponse(Call<FixerInformations> call, Response<FixerInformations> response) {
                if (response.isSuccessful()){
                    Log.e(TAG,"Message:" +response.code());
                }else{
                    Log.e(TAG,"AQui");
                    FixerInformations informations = response.body();
                    Log.e(TAG,String.format("%s",informations));
//                    for (Exchange exc: informations){
//                        Log.e(TAG,String.format("$s",exc.date));
//                    }


                }

            }

            @Override
            public void onFailure(Call<FixerInformations> call, Throwable t) {
                Log.e(TAG,"Error: "+ t.getMessage());
            }
        });
    }
}
