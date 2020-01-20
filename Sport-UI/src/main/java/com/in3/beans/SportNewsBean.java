package com.in3.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.in3.dto.SportingNewsDTO;

@Named
public class SportNewsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
    private Environment env;
	
	private String word;
	private String source;

	private List<SportingNewsDTO> data;

	@PostConstruct
	public void init() {
		
	}

	public void getNews() {

		RestTemplate restTemplate = new RestTemplate();

		try {
			String uri = env.getProperty("sports.news.Service.connection.url")+"/getData";
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.set("keyword", word);
			params.set("source", source);
			
			String targetUri = buildURI(uri, params);

			System.out.println(targetUri);
			data = restTemplate
					.exchange(targetUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<SportingNewsDTO>>() {
					}).getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String buildURI(String uri, MultiValueMap<String, String> params) {
		String url = UriComponentsBuilder.fromHttpUrl(uri).queryParams(params).build().toUriString();
		return url;
	}

	// Getters&Setters

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<SportingNewsDTO> getData() {
		return data;
	}

	public void setData(List<SportingNewsDTO> data) {
		this.data = data;
	}

}
