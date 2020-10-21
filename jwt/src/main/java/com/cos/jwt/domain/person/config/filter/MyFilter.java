package com.cos.jwt.domain.person.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.weaver.patterns.HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor;

public class MyFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;		
		resp.setHeader("Access-Control-Allow-Origin", "*");
		chain.doFilter(request, response);

	}
	
	
	
////필터 생성시 처리
//@Override
//public void init(FilterConfig filterConfig) throws ServletException {
//	// TODO Auto-generated method stub
//	Filter.super.init(filterConfig);
//}

//
////	필터 제거시 처리
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//		Filter.super.destroy();
//	}

}
