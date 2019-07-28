
package com.example.demo.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SysRoleDao;
import com.example.demo.entity.SysRole;
import com.example.demo.service.SysRoleService;

@Service(value = "SysRoleServiceImpl")
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	SysRoleDao SysRole;

	@Override
	public List<SysRole> findAll() {
		return SysRole.findAll();
	}

	@Override
	public SysRole findByid(Integer sysRole) {
		
		return SysRole.findByid(sysRole);
	}

}
