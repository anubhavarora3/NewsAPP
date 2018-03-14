package com.falcon.anubhav.newsapp.main.main;

import com.falcon.anubhav.newsapp.main.model.SourceList;
import com.falcon.anubhav.newsapp.main.model.Sources;
import com.falcon.anubhav.newsapp.main.rest.NewsApiInterface;
import com.falcon.anubhav.newsapp.main.rest.NewsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anubhav on 14/03/18.
 */

public class MainInteractor implements IMainInteractor{

    private final String apiKey = "f926bb3ad1d0472f97651eeb3df8967e";
    private List<Sources> sources;

    @Override
    public void fetchItems(final OnFinishedListener finishedListener) {

        NewsApiInterface apiService = NewsService.getRetrofit().create(NewsApiInterface.class);

        Call<SourceList> call = apiService.getSourceList(apiKey);
        call.enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList> call, Response<SourceList> response) {
                if (response.isSuccessful()) {
                    sources = response.body().getSources();
                    finishedListener.onSuccess(sources, response.message().toString());
                } else {
                    finishedListener.onFailure(response.message().toString());
                }
            }

            @Override
            public void onFailure(Call<SourceList> call, Throwable t) {
                finishedListener.onFailure(t.getMessage());
            }
        });
    }
}
