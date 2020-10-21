package com.cos.jwt.domain.person;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor //디펄트 생성자 만드는용도
@AllArgsConstructor //풀 생성자 만드는 용도
@Builder
@Data //get,set자동설정
@Entity //테이블생성
public class Person {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	@Column(unique = true)
	private String username;
	private String password;
	private String email;
	private String role; //권한용
}
