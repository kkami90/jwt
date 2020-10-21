package com.cos.jwt.domain.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PersonRepository extends JpaRepository<Person, Integer>{
	
	@Query(value = "select * from person where username = :name and password = :pass",nativeQuery = true)
	Person mFindBynameOrpass(String name, String pass);


}
