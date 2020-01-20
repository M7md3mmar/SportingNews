package com.in3.SportData.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in3.SportData.dto.SportingNewsDTO;
import com.in3.SportData.service.SportNewsService;

/**
 * @author Ammar
 * @version 1.0 .
 * @since 21/1/2020
 */

@RestController
@RequestMapping(value = "/sportNews")
public class SportNewsController {
	@Autowired
    private SportNewsService sportNewsService;

    


    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public List<SportingNewsDTO> getData(@RequestParam(value = "keyword") String searchKewWord, @RequestParam(value = "source") String source) {
        return sportNewsService.getDataNews(searchKewWord, source);
    }

}
