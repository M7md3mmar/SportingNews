package com.in3.SportData.dao;

import com.in3.SportData.dto.SportingNewsDTO;

import java.util.List;

public interface SportsDataDAO {
    List<SportingNewsDTO> getSportNews(String keyWord, String source);
    boolean addSportNews(List<SportingNewsDTO> listFeed);
}
