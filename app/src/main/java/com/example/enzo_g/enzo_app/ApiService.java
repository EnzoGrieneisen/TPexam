package com.example.enzo_g.enzo_app;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by enzo_g on 15/01/18.
 */

public interface ApiService {
    @GET("vehicles/{id}")
    Call<Vehicles> readVehicle(@Path("id") final int id);

    @GET("starships/{id}")
    Call<Ships> readShips(@Path("id") final int id);

    class Builder {
        /**
         * Create a singleton only for simplicity. Should be done through a DI system instead.
         */
        private static final ApiService instance = build();

        public static ApiService getInstance() {
            return instance;
        }

        private Builder() {
        }
        private static ApiService build() {
            final Gson gson = new GsonBuilder().create(); // JSON deserializer/serializer

            // Create the OkHttp Instance
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(final Chain chain) throws IOException {
                            final Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            return new Retrofit.Builder()
                    .baseUrl("https://swapi.co/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ApiService.class);
        }
    }
}