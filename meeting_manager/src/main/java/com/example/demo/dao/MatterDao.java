package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Matter;

public interface MatterDao extends JpaRepository<Matter, Integer>, JpaSpecificationExecutor<Matter> {

	public List<Matter> findByDeptnoId(String deptnoId);
	
	@Query("from Matter where matterPassword=:#{#ispassword} and matterId=:#{#matterid}")
	public Matter findBypasswordAndmatterid(String ispassword, Integer matterid);

	public Matter findByMatterId(Integer matterId);
	
}
