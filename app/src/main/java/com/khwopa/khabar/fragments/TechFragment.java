package com.khwopa.khabar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khwopa.khabar.BuildConfig;
import com.khwopa.khabar.R;
import com.khwopa.khabar.api.Api;
import com.khwopa.khabar.api.ApiServices;
import com.khwopa.khabar.models.NewsResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechFragment extends PopularFragment {
    @Override
    protected void getData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        String day = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(cal.getTimeInMillis()));

        ApiServices apiServices = Api.getRetrofitInstance().create(ApiServices.class);
        Call<NewsResponse> call = apiServices.getTopHeadlines(
                "technology",
                day,
                "in",
                "en",
                "popularity",
                100,
                BuildConfig.NewsApiKey
        );
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    generateList(response.body());
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                noNetwork.setVisibility(View.VISIBLE);
            }
        });
    }
}
