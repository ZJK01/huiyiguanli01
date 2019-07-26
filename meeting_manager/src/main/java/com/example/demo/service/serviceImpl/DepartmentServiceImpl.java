package com.example.demo.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@Service(value = "DepartmentServiceImpl")
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;

	@Override
	public List<Department> findallByDepartmentid(String did) {
		// TODO Auto-generated method stub
		return departmentDao.findAllBydepartmentId(did);
	}

	@Override
	public List<Department> findallByDepartmentidNot(String did) {
		// TODO Auto-generated method stub
		return departmentDao.findAllBydepartmentIdNot(did);
	}

	

}
