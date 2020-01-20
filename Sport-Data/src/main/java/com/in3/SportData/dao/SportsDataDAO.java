package com.in3.SportData.dao;

import com.in3.SportData.dto.SportingNewsDTO;

import java.util.List;

public interface SportsDataDAO {
    /**
     * To get Sporting News
     * @param keyWord
     * @param source
     * @return  List<SportingNewsDTO>
     */
    List<SportingNewsDTO> getSportNews(String keyWord, String source);
    /**
     * To add Sport news feeds
     * @param listFeed
     * @return
     */
    boolean addSportNews(List<SportingNewsDTO> listFeed);
}
