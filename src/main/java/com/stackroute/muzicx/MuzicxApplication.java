package com.stackroute.muzicx;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
@EnableCaching
@SpringBootApplication
@Slf4j
public class MuzicxApplication {

	public static void main(String[] args) {
		log.debug("This is a main debug message");
		SpringApplication.run(MuzicxApplication.class,args);
	}
		@Bean
		public ModelMapper modelMapper () {
			return new ModelMapper();
		}


}
