package com.in3.SportData.configuration;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.in3.SportData.utils.SportsNewsConstants;

@Configuration
public class SportsNewsConfiguration {
	
	@Autowired
    private Environment env;

    


    @Bean(name = "sportDataSolrClient")
    public SolrClient getSportDataSolrClient() throws IOException, SolrServerException {
        SolrClient client = new HttpSolrClient(env.getProperty(SportsNewsConstants.Solr.SPORTS_NEWS_SOLR_CONNECTION_URL));
        return client;
    }

}
