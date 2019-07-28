package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;

import org.springframework.data.domain.Page;

public interface DepartmentService {
	// 查询所有部门
	public List<Department> findAll();

	// 查询除某个部门外所有部门
	public List<Department> findAllDepartmentIdNot(Integer idInteger);

	// 查询单个部门
	public List<Department> findAllDepartmentId(Integer idInteger);

	// 根据部门id 查找部门所有员工
	public List<Employee> findByDepartmentId(Integer idInteger);

	public List<Department> like(String workset); // 模糊查询

	public List<Department> likeDepartmentname(String departmentname);// 模糊查询

	// 删除部门
	public void deleteById(Integer id);

	// 添加部门
	public void save(Department dp);

	// 获取部门总数
	public Integer count();

	// 分页
	public Page<Department> fy(Integer pageCurrent, Integer pageCount);



	Department findDeptnoId(String departMent);
}
