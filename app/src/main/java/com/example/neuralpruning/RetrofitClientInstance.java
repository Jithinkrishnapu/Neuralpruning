package com.example.neuralpruning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static String API_BASE_URL="https://neuralpruning.com/api/";
 public static Retrofit getRetrofit(){
     HttpLoggingInterceptor httpLoggingInterceptor =new HttpLoggingInterceptor();
     httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

     OkHttpClient okHttp = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

     Retrofit retrofit = new Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl(API_BASE_URL)
             .client(okHttp)
             .build();
     return retrofit;
 }
 public static UserService getService(){
     UserService userService=getRetrofit().create(UserService.class);
     return  userService;
 }
}
