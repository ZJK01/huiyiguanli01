package com.example.demo.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@Service(value="DepartmentServiceImpl")
public class DepartmentServiceImpl  implements DepartmentService {

	@Autowired
	DepartmentDao departmentdao;
	
	@Override
	public List<Department> findAll() {
		return departmentdao.findAll();
	}


	@Override
	public List<Department> findAllDepartmentIdNot(String idInteger) {
		
		return departmentdao.findAllBydepartmentIdNot(idInteger);
	}

}
