package com.example.app.appfixerio.services;

import com.example.app.appfixerio.models.Exchange;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by joao on 18/07/17.
 */

public interface ConsumeService {

    public static final String BASE_URL = "http://api.fixer.io/";

    /**
     * This function expected two params
     * String: YYYY-MM-DD
     * base: string "example: USD"
     */

    @GET("{date}")
    Call<Exchange> listInformation(@Path(value = "date", encoded = true) String date, @Query("base") String extended);


}
