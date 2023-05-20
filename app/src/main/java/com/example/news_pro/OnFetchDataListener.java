package com.example.news_pro;

import com.example.news_pro.Models.NewsHeadlines;

import java.util.List;

public interface  OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
