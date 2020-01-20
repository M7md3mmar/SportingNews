package com.in3.SportData.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.in3.SportData.dto.SportingNewsDTO;
import com.in3.SportData.service.SportNewsService;
import com.in3.SportData.utils.Utils;

/**
 * @author Ammar
 * @since 21/1/2020
 * @version 1.0 .
 */
@Component
public class CrawlingServiceScheduler 
{
	 private Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	SportNewsService sportNewsService;

	private Set<String> links = new HashSet<String>();

	/**
	 * Crawl from Shorouk
	 */
	@Scheduled(fixedRate = 1800000) // Run every 0.5 hour
	public void crawlShorouk() {
		getPageLinks("https://www.shorouknews.com/");
		List<SportingNewsDTO> listData = getData("shorouk");
		addSolrDocument(listData);

	}

	/**
	 * Crawl from Yallakora
	 */
	@Scheduled(fixedRate = 1800000) // Run every 0.5 hour
	public void crwalYallakora() {
		getPageLinks("https://www.yallakora.com/");
		List<SportingNewsDTO> listData = getData("yallakora");
		addSolrDocument(listData);
	}



	/**
	 * Get All Links related to URL
	 * @param URL
	 */
	public void getPageLinks(String URL) {

		if (!links.contains(URL)) {
			try {

				Document document = Jsoup.connect(URL).get();
				String subLink = "";
				if (URL.contains("www.shorouknews.com"))
					subLink = "a[ href^=\"/sports\"]";
				else if (URL.contains("yallakora"))
					subLink = "a[ href^=\"/NewsListing/%D8%A7%D9%84%D8%A3%D8%AE%D8%A8%D8%A7%D8%B1#nav-menu\"]";

				Elements otherLinks = document.select(subLink);
				for (Element page : otherLinks) {
					links.add(URL);
					getPageLinks(page.attr("abs:href"));
				}
			} catch (IOException e) {
				logger.error("Error", e);
			}
		}
	}

	/**
	 * To get each title and its link
	 * @param source
	 * @return List<SportingNewsDTO>
	 */
	public List<SportingNewsDTO> getData(String source) {
		List<SportingNewsDTO> resultList = new ArrayList<>();
		Document document;
		String checkingValue = "";
		if ("shorouk".equalsIgnoreCase(source))
			checkingValue = "/news/view.aspx";
		else if ("yallakora".equalsIgnoreCase(source))
			checkingValue = "News";

		try {
			for (String link : links) {
				document = Jsoup.connect(link).get();
				Elements articleLinks = document.select("a[href]");
				for (Element article : articleLinks) {
					if (article.text() != null && article.text() != ""
							&& article.attr("abs:href").contains(checkingValue)) {
						if (!"yallakora".equalsIgnoreCase(source) && !Utils.checkSportingWords(article.text())) {
							continue;
						} else {
							SportingNewsDTO dto = new SportingNewsDTO();
							dto.setTitle(article.text()); // The title of the article
							dto.setLink(article.attr("abs:href")); // The URL of the article
							dto.setSource(source);
							dto.setDate(Utils.prepareSolrTime(new Date()));
							dto.setId(dto.getLink() + "~|~" + dto.getSource());
							resultList.add(dto);

							logger.info(dto.getTitle() + dto.getLink());

						}
					}

				}
			}
		} catch (IOException e) {
			logger.error("Error", e);
		}
		return resultList;
	}

	/**
	 * Pass List of DTOs to Service layer
	 * @param data
	 */
	public void addSolrDocument(List<SportingNewsDTO> data) {
		sportNewsService.addSolrDocument(data);
	}

}
