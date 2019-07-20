package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Matter;

public interface MatterService {
	public void save(Matter matter);

	public List<Matter> findBydeptnoId(String deptnoId);

	public Matter findBypasswordAndmatterid(String ispassword, Integer matterid);


	public Matter findBymatterId(Integer matterId);
}
