package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Manager;

public interface ManagerDao extends JpaRepository<Manager,Integer>,JpaSpecificationExecutor<Manager> {

	@Query("from Manager where managerName=:#{#manager.managerName} and managerPassword=:#{#manager.managerPassword}")
	public Manager findByManagerNameAndManagerPassword(Manager manager);
	
}
