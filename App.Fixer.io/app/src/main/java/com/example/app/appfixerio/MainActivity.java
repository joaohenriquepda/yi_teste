package com.example.app.appfixerio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.app.appfixerio.models.Exchange;
import com.example.app.appfixerio.models.FixerInformations;

import java.text.ParseException;
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
        ConsumeService service = retrofit.create(ConsumeService.class);


        SimpleDateFormat formato =  new SimpleDateFormat("yyyy-mm-dd");
        Date dt1 = null;
        try {
            dt1 = formato.parse("2009-08-07");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dt2 = null;
        try {
            dt2 = formato.parse("2011-11-17");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime (dt1);
        for (Date dt = dt1; dt.compareTo (dt2) <= 0; ) {
            Log.e(TAG,formato.format(dt));
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
                    }
                }
                @Override
                public void onFailure(Call<Exchange> call, Throwable t) {
                    Log.e(TAG,"Error: "+ t.getMessage());
                }
            });

        }
    }
}
