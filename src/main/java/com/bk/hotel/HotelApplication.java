package com.bk.hotel;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}
	
	@Bean
	public List<String> roomTypes(){
		return Arrays.asList("Single", "Double", "Suite");
	}
}
