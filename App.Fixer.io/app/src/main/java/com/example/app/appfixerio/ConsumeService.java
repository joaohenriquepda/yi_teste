package com.example.app.appfixerio;

/**
 * Created by joao on 18/07/17.
 */

public interface ConsumeService {

    public static final String BASE_URL = "http://api.fixer.io/";

    /**
     * This function expected two params
     * date: YYYY-MM-DD
     * base: string "example: USD"
     */

    @GET("{date}")
    Call<FixerInformations> listInformation(@Path(value = "date", encoded = true) Date date, @Query("base") String extended);


}
