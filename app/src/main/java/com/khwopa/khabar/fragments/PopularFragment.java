package com.khwopa.khabar.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.khwopa.khabar.BuildConfig;
import com.khwopa.khabar.R;
import com.khwopa.khabar.adapters.NewsAdapter;
import com.khwopa.khabar.api.Api;
import com.khwopa.khabar.api.ApiServices;
import com.khwopa.khabar.models.Articles;
import com.khwopa.khabar.models.NewsResponse;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment {

    private View v;
    private RecyclerView rv;
    private NewsAdapter adapter;
    protected ProgressBar progressBar;
    protected LinearLayout noNetwork;

    public PopularFragment() { }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list, container, false);

        noNetwork = v.findViewById(R.id.noNetwork);

        progressBar = v.findViewById(R.id.loadingAnim);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        progressBar.setVisibility(View.VISIBLE);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    protected void getData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        String day = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                .format(new Date(cal.getTimeInMillis()));

        ApiServices apiServices = Api.getRetrofitInstance().create(ApiServices.class);
        Call<NewsResponse> call = apiServices.getEveryNews("nepal",
                day,
                "en",
                null,
                "popularity",
                100,
                BuildConfig.NewsApiKey
        );
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.isSuccessful()) {
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

    protected void generateList(@NonNull NewsResponse newsResponse) {
        rv = v.findViewById(R.id.newsRecycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(newsResponse.getArticles(), new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Articles article) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                        .addDefaultShareMenuItem()
                        .setShowTitle(true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(Objects.requireNonNull(getActivity()), Uri.parse(article.getUrl()));
            }
        });
        rv.setAdapter(adapter);
    }
}
