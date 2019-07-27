
package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, Integer>, JpaSpecificationExecutor<SysRole> {

}
