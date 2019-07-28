package com.example.demo.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public List<Department> findAllDepartmentIdNot(Integer idInteger) {
		return departmentdao.findAllBydepartmentIdNot(idInteger);
	}


	public List<Department> like(String workset ) {
		List<Department> like=departmentdao.like(workset);		
		List<Department>  likeDepartmentname=departmentdao.likeDepartmentname(workset);
		like.addAll(likeDepartmentname);
		return like;
	}

	
	@Override
	public List<Department> likeDepartmentname(String departmentname) {
		List<Department>  likeDepartmentname=departmentdao.likeDepartmentname(departmentname);
		return likeDepartmentname;
	}

	@Override
	public void deleteById(Integer id) {
		departmentdao.deleteById(id);
	}

	@Override
	public void save(Department dp) {
		departmentdao.save(dp);
	}


	@Override
	public Integer count() {
		Integer dp= (int) departmentdao.count();
		return dp;
	}


	@Override
	public Page<Department> fy(Integer pageCurrent, Integer pageCount) {
		PageRequest pageable = new PageRequest(pageCurrent, pageCount);
		Page<Department> dp = departmentdao.findAll(pageable);		
		return dp;
	}


	@Override
	public Department findDeptnoId(String departMent) {
		return departmentdao.findBydepartmentId(Integer.parseInt(departMent));
		
	}

}
