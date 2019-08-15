package com.khwopa.khabar.api;

import com.khwopa.khabar.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface
ApiServices {
    @GET("/v2/everything")
    Call<NewsResponse> getEveryNews(@Query("q") String q,
                                    @Query("from") String from,
                                    @Query("language") String language,
                                    @Query("sources") String sources,
                                    @Query("sortBy") String sortBy,
                                    @Query("pageSize") int pageSize,
                                    @Query("apiKey") String apiKey
    );

    @GET("/v2/top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("category") String category,
                                    @Query("from") String from,
                                    @Query("country") String country,
                                    @Query("language") String language,
                                    @Query("sortBy") String sortBy,
                                    @Query("pageSize") int pageSize,
                                    @Query("apiKey") String apiKey
    );
}
