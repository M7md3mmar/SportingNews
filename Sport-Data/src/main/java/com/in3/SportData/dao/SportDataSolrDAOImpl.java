package com.in3.SportData.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.in3.SportData.utils.SportsNewsConstants;
import com.in3.SportData.utils.Utils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.in3.SportData.dto.SportingNewsDTO;

/**
 * @author Ammar
 * @version 1.0 .
 * @since 21/1/2020
 */

@Repository
public class SportDataSolrDAOImpl implements SportsDataDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SolrClient sportDataSolrClient;
    
    

    @Override
    public boolean addSportNews(List<SportingNewsDTO> listFeed) {
        logger.info("SolrFeedDAO.SportDataSolrDAO()-----------------------------------Start");
        boolean flag = true;
        int count = 0;
        try {

            if (listFeed != null && listFeed.size() > 0) {
                List<SolrInputDocument> listDocument = new ArrayList<>();
                SolrInputDocument doc = null;
                for (SportingNewsDTO dto : listFeed) {
                    doc = new SolrInputDocument();
                    doc.addField(SportsNewsConstants.Solr.ID, dto.getId());
                    doc.addField(SportsNewsConstants.Solr.LINK, dto.getLink());
                    doc.addField(SportsNewsConstants.Solr.TITLE, dto.getTitle());
                    doc.addField(SportsNewsConstants.Solr.SOURCE, dto.getSource());
                    doc.addField(SportsNewsConstants.Solr.DATE, dto.getDate());
                    listDocument.add(doc);
                    count++;
                    if (count == 100) {
                        sportDataSolrClient.add(listDocument);
                        count = 0;
                    }
                }
                if (count > 0)
                    sportDataSolrClient.add(listDocument);
                sportDataSolrClient.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        logger.info("SportDataSolrDAO.SportDataSolrDAO()-----------------------------------End");
        return flag;

    }   

    @Override
    public List<SportingNewsDTO> getSportNews(String keyWord, String source) {
        logger.info("SportDataSolrDAO.getSportNewsData()-----------------------------------Start");
        List<SportingNewsDTO> result = null;
        try {
            if (keyWord != null && !"".equalsIgnoreCase(keyWord)) {
                SolrQuery solrQuery = getKeywordQuery(keyWord, source);
                QueryResponse queryResponse = sportDataSolrClient.query(solrQuery);
                SolrDocumentList docs = queryResponse.getResults();
                if (queryResponse != null && docs != null && !docs.isEmpty()) {
                    result = fillResult(docs);
                }
            }
        } catch (Exception e) {
            logger.error("Error", e);
        }
        logger.info("SportDataSolrDAO.getSportNewsData()-----------------------------------End");
        return result;
    }
    
    

    /**
     * Fill returned Solr document list  to LIST OF DTOs
     * @param docs
     * @return list of DTOs
     */
    protected List<SportingNewsDTO> fillResult(SolrDocumentList docs) {
        List<SportingNewsDTO> result;
        SportingNewsDTO dto;
        result = new ArrayList<>();
        for (SolrDocument solrDocument : docs) {
            dto = getSportingNewsDTO(solrDocument);
            result.add(dto);
        }
        return result;
    }

    /**
     * Helper Method  to prepare the returned result from solr
     * @param solrDocument
     * @return SportingNewsDTO
     */
    protected SportingNewsDTO getSportingNewsDTO(SolrDocument solrDocument) {
        SportingNewsDTO dto = new SportingNewsDTO();
        dto.setId(Utils.getSafeString((String) solrDocument.getFieldValue(SportsNewsConstants.Solr.ID)));
        dto.setTitle(Utils.getSafeString((String) solrDocument.getFieldValue(SportsNewsConstants.Solr.TITLE)));
        dto.setSource(Utils.getSafeString((String) solrDocument.getFieldValue(SportsNewsConstants.Solr.SOURCE)));
        dto.setLink(Utils.getSafeString((String) solrDocument.getFieldValue(SportsNewsConstants.Solr.LINK)));
        dto.setDate(Utils.getSafeString((Date) solrDocument.getFieldValue(SportsNewsConstants.Solr.DATE)));
        return dto;
    }

    /**
     * Helper Method to prepare solr query
     * @param keyWord
     * @param source
     * @return  SolrQuery
     */
    protected SolrQuery getKeywordQuery(String keyWord, String source) {
        SolrQuery solrQuery = new SolrQuery();
        StringBuilder q = new StringBuilder(SportsNewsConstants.Solr.TITLE + ":" + keyWord + "*" + " OR " + SportsNewsConstants.Solr.TITLE + ":*" + keyWord + "*");
        if (source != null && !source.isEmpty())
            q.append(" AND source:" + source);

        solrQuery.add("q", q.toString());
        solrQuery.addSort(SportsNewsConstants.Solr.DATE, ORDER.desc);
        return solrQuery;
    }



}
