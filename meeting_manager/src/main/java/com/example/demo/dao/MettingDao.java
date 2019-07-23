package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Matter;
import com.example.demo.entity.Meeting;

public interface MettingDao extends JpaRepository<Meeting, Integer>,JpaSpecificationExecutor<Matter> {
	
	@Query("from Meeting where startTime>:#{#firstMonth} and startTime<:#{#lastMonth}")
	List<Meeting> findTime(String firstMonth, String lastMonth);
}
