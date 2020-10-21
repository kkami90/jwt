package com.cos.jwt.domain.person.config.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
		HttpServletResponse resp = (HttpServletResponse)response;
		PrintWriter out = resp.getWriter();
		
		
		String method = req.getMethod();
		System.out.println("req = "+req.getMethod());
		if(!method.equals("POST")) {
			System.out.println("post요청 아님");
			out.print("Post request. error");
			out.flush();
			return ;
		}else {
			System.out.println("post요청확인");
			
			ObjectMapper om = new ObjectMapper();
			Person person = om.readValue(req.getInputStream(), Person.class);
			System.out.println("들어온 person 값 = "+person);
			
			Person p = personRepository.mFindBynameOrpass(person.getUsername(), person.getPassword());
			
			if(p != null) {
				System.out.println("일치 id = "+p.getId());
				
				String jwtToken = JWT.create().withSubject("코스토큰")
						.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10))
						.withClaim("id", p.getId())
						.withClaim("username", p.getUsername())
						.sign(Algorithm.HMAC512("코스"));
				//여기 키값이랑 토큰앞 Bearer은 정해진 규악. jwt문서가면 나와있다.
				resp.addHeader("Authorizaion", "Bearer "+jwtToken);
				out.print("ok");
				out.flush();
				
				
				
				
//				아래방식이 원래의 방식이나 복잡해서 라이브러리 사용
//				String jwtHeader = "{\"alg\" : \"HS256\"}";
//				String jwtMsg = "{\"userId\" : "+p.getId()+"}";
				
			}else {
				System.out.println("유저 이름 또는 비밀번호가 틀렸거나 없는 사용자입니다");
				out.print("fail");
				out.flush();
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
