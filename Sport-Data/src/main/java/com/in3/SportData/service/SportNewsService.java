package com.in3.SportData.service;

import java.util.List;

import com.in3.SportData.dao.SportsDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in3.SportData.dao.SportDataSolrDAOImpl;
import com.in3.SportData.dto.SportingNewsDTO;

/**
 * @author Ammar
 * @version 1.0 .
 * @since 21/1/2020
 */
@Service
public class SportNewsService {

	@Autowired
    SportsDataDAO sportDataSolrDAO;

    

    /**
     * @param data
     * Service to pass list of SportingNewsDTO to dao layer
     */
    public void addSolrDocument(List<SportingNewsDTO> data) {
        sportDataSolrDAO.addSportNews(data);
    }

    /**
     * @param keyWord
     * @param source
     * @return  List<SportingNewsDTO>
     */
    public List<SportingNewsDTO> getDataNews(String keyWord, String source) {
        return sportDataSolrDAO.getSportNews(keyWord, source);
    }
}
