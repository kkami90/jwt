package com.cos.jwt.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cos.jwt.domain.person.Person;
import com.cos.jwt.domain.person.PersonRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class IndexController {
	
	private final PersonRepository personRepository;
	
	
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}
	
	@PostMapping("/joinProc")
	public String 회원가입(@RequestBody Person person) {
		personRepository.save(person);
		return "ok";
	}
	
//	@CrossOrigin(origins = "http://127.0.0.1:5500",methods = RequestMethod.POST) //
	@GetMapping("/joinProc/{id}")
	public ResponseEntity<?> 회원상세(@PathVariable int id, HttpServletResponse response, 
			HttpServletRequest request) {
		//첫번째 인자는 정해진 규약. 2번째는 모든 대상에게 한다는 대상 설정.
//		response.setHeader("Access-Control-Allow-Origin", "*");
	
		String jwtToken = request.getHeader("Authorization");
		System.out.println("회원정보 = " +jwtToken);
		
		jwtToken = jwtToken.replace("Bearer ", "");
		
		DecodedJWT decode =  JWT.require(Algorithm.HMAC512("코스")).build().verify(jwtToken);
		int personId = decode.getClaim("id").asInt();
		if(personId == 0) {
			return new ResponseEntity<String>("인증안됨",HttpStatus.FORBIDDEN);
		}else {
//		return personRepository.findById(id).get();
			return new ResponseEntity<Person>
			(personRepository.findById(id).get(),HttpStatus.OK);
		}
	}
	
	

}
