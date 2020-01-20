package com.in3.SportData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.in3.SportData.service.SportNewsService;
import com.in3.SportData.dao.*;

@SpringBootTest
class SportDataApplicationTests {

	@InjectMocks
	private SportNewsService sportNewsService;
	 @Mock
	private SportsDataDAO SportsDataDAO;


	@Test
	void getFoundKeyWord() {
		assertTrue(sportNewsService.getDataNews("الزمالك", "") != null);
	}

	@Test
	void getNotFoundKeyWord() {
		assertFalse(sportNewsService.getDataNews("AAAAAAAAA", "").size() > 0);
	}

}
