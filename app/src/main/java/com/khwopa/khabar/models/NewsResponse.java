package com.khwopa.khabar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("articles")
    @Expose
    private List<Articles> articles;

    public NewsResponse(){}

    public NewsResponse(String status, int totalResults, List<Articles> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public NewsResponse(@org.jetbrains.annotations.NotNull NewsResponse news){
        this.status = news.status;
        this.totalResults = news.totalResults;
        this.articles = new ArrayList<>();
        this.articles.addAll(news.articles);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

}