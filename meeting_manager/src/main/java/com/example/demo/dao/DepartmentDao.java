package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;

public interface DepartmentDao extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {


	public List<Department> findAllBydepartmentId(Integer did);

	public List<Employee> findBydepartmentId(Integer did);

	public List<Department> findAllBydepartmentId(String did);

	public List<Department> findAllBydepartmentIdNot(String did);
	
	@Query("From Department where departmentId!=:#{#idInteger}")
	public List<Department> findAllBydepartmentIdNot(Integer idInteger);

	@Query("FROM Department WHERE work_set LIKE %:#{#workset}%")
	public List<Department> like(@Param("workset")String workset);
    
    @Query("FROM Department WHERE depart_ment_name LIKE %:#{#departmentname}%") 
   	public List<Department> likeDepartmentname(@Param("departmentname")String departmentname);

	public Department findBydepartmentId(int parseInt);

}
