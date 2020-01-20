package com.in3.SportData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author Ammar
 * @since 21/1/2020
 * @version 1.0 .
 */
@SpringBootApplication
@EnableScheduling
public class SportDataApplication {


	public static void main(String[] args)
	{
		SpringApplication.run(SportDataApplication.class, args);

	}

	
}
