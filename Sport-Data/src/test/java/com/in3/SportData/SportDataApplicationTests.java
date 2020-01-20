package com.in3.SportData;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.in3.SportData.dao.SportDataSolrDAOImpl;
import com.in3.SportData.service.SportNewsService;

@SpringBootTest
class SportDataApplicationTests {

	@Test
	void contextLoads()
	{

	}
	
	  @Mock
	  private SportNewsService sportNewsService;

	  @InjectMocks
	  private SportDataSolrDAOImpl sportDataSolrDAOImpl;

	  @Test
	  void getSportNews() {
		  
		  assertTrue( sportNewsService.getDataNews("الزمالك", "")!=null);
	  }

}
