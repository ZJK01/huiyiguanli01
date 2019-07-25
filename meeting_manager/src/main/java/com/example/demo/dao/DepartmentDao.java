package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.Department;


public interface DepartmentDao extends JpaRepository<Department, Integer>,JpaSpecificationExecutor<Department> {

	public List<Department> findAllBydepartmentId(String did);

	public List<Department> findAllBydepartmentIdNot(String did);

	public List<Department> findAllBydepartmentIdNot(Integer idInteger);

}
