package com.sagar.Dentia.Dentia.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sagar.Dentia.Dentia.Entities.Service;

public interface ServiceDao extends JpaRepository<Service, Integer> {
	
	@Query("from Service as s where s.client.id = :id")
	public List<Service> findServiceById(@Param("id") int id);
}
