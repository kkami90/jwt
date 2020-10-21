package com.cos.jwt.domain.person.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cos.jwt.domain.person.PersonRepository;
import com.cos.jwt.domain.person.config.filter.MyFilter;
import com.cos.jwt.domain.person.config.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
public class FilterConfig{
	
	private final PersonRepository personRepository;
	
	@Bean
	public FilterRegistrationBean<MyFilter> corsFilter(){
		FilterRegistrationBean<MyFilter> bean= new FilterRegistrationBean<>(new MyFilter());
		bean.addUrlPatterns("/*"); //대상?
		bean.setOrder(0);
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter(){
		FilterRegistrationBean<JwtAuthenticationFilter> bean= new FilterRegistrationBean<>(new JwtAuthenticationFilter(personRepository));
		bean.addUrlPatterns("/loginProc"); //대상
		
		bean.setOrder(1);
		return bean;
	}
}
