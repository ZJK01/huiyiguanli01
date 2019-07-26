package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
	List<Department> findAll();

	List<Department> findAllDepartmentIdNot(String idInteger);

	public List<Department> findallByDepartmentid(String did);

	public List<Department> findallByDepartmentidNot(String did);
	
}
