package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 部门表
 */
@Entity
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer departmentId; // 部门编号
	private String departMentName; // 部门名称
	private String workSet; // 部门办公地点

	@OneToMany(mappedBy = "department")
	private List<Employee> employees;

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartMentName() {
		return departMentName;
	}

	public void setDepartMentName(String departMentName) {
		this.departMentName = departMentName;
	}

	public String getWorkSet() {
		return workSet;
	}

	public void setWorkSet(String workSet) {
		this.workSet = workSet;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Department(Integer departmentId, String departMentName, String workSet, List<Employee> employees) {
		super();
		this.departmentId = departmentId;
		this.departMentName = departMentName;
		this.workSet = workSet;
		this.employees = employees;
	}

	public Department() {
		super();
	}

}
