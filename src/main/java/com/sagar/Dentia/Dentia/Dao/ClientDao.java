package com.sagar.Dentia.Dentia.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sagar.Dentia.Dentia.Entities.Client;

public interface ClientDao extends JpaRepository<Client, Integer>{
	
	
	@Query("select c from Client c where c.email = :email")
	public Client getEmailByUserName(@Param("email") String email);

}
