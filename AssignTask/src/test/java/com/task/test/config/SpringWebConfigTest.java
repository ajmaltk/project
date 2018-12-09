package com.task.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import com.task.service.ScheduleService;
import com.task.service.ScheduleSeviceImpl;

/**
 * 
 * 
 * JUnit spring configuration class
 *
 */
@Configuration
@ComponentScan(basePackages = "com.task")
public class SpringWebConfigTest extends WebMvcConfigurerAdapter {

	
	@Bean
	public ScheduleService getScheduleService() {
	
	        return new ScheduleSeviceImpl();
	
	    }

}
