package com.cos.jwt.domain.person.config.jwt;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


import com.cos.jwt.domain.person.Person;
import com.cos.jwt.domain.person.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JwtAuthenticationFilter implements Filter{
	
	
	private PersonRepository personRepository;
	
	public JwtAuthenticationFilter(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("jwtFilter작동됨");
		HttpServletRequest req = (HttpServletRequest)request;
		String method = req.getMethod();
		System.out.println("req = "+req.getMethod());
		if(!method.equals("POST")) {
			System.out.println("post요청 아님");
			return ;
		}else {
			System.out.println("post요청확인");
			
			ObjectMapper om = new ObjectMapper();
			Person person = om.readValue(req.getInputStream(), Person.class);
			System.out.println("들어온 person 값 = "+person);
			
			Person p = personRepository.mFindBynameOrpass(person.getUsername(), person.getPassword());
			
			if(p != null) {
				System.out.println("일치 id = "+p.getId());
			}else {
				System.out.println("유저 이름 또는 비밀번호가 틀렸거나 없는 사용자입니다");
			}
			
			
			
//			List<Person> list= personRepository.findAll();
//			for(int i=0; i<list.size();i++) {
//				Person p = list.get(i);
//				if(p.getUsername().equals(person.getUsername()))
//				System.out.println("이름 일치"); 
//				if(p.getPassword().equals(person.getPassword()))
//					System.out.println("비번 일치");
//			}
			
//			1. username,pas를 db 던지고
//			2. db에 값이 있는지 없는지 확인하기
//			3. 이걸하는 이유가 현재 Person레파지토리가 작동을 안할거라 그걸 해결해보라는거같음
		}
		
	}
	

}
