package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
	List<Department> findAll();

	List<Department> findAllDepartmentIdNot(Integer idInteger);

	public List<Department> findallByDepartmentid(Integer did);

//	public List<Department> findallByDepartmentidNot(Integer did);
	
}
