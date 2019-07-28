package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.entity.Department;

public interface DepartmentService {
	
	List<Department> findAll();

	List<Department> findAllDepartmentIdNot(Integer idInteger);
	
	public List<Department> like(String workset); //模糊查询
	
	public List<Department> likeDepartmentname(String departmentname);//模糊查询

	public void deleteById(Integer id);

	public void save(Department dp);

	public Integer count();

	Page<Department> fy(Integer pageCurrent, Integer pageCount);

	Department findDeptnoId(String departMent);
}
