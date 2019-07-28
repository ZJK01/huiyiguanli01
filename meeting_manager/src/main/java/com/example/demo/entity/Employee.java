package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 员工表
 */
@Entity
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employeeId; // 员工编号
	private String employeeName; // 员工姓名
	private String employeeAccount; // 员工账户
	private String employeePassword; // 登录密码
	private String power; // 权力
	private String employeeEmail; // 邮箱

	@ManyToOne
	@JoinColumn(name = "departmentid")
	private Department Department;

	@OneToMany(mappedBy = "employee")
	private List<Matter> files; // 设置和文件表的级联

	@ManyToMany(fetch = FetchType.EAGER) // 立即从数据库中进行加载数据;
	@JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "employeeId") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	private List<SysRole> roleList; // 一个用户有多个角色

	@OneToMany(mappedBy = "employeeId")
	private List<Meeting> meetingreservAtionuserId;

	public Employee(Integer employeeId, String employeeName, String employeeAccount, String employeePassword,
			String power, String employeeEmail, com.example.demo.entity.Department department, List<Matter> files,
			List<SysRole> roleList, List<Meeting> meetingreservAtionuserId) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeAccount = employeeAccount;
		this.employeePassword = employeePassword;
		this.power = power;
		this.employeeEmail = employeeEmail;
		Department = department;
		this.files = files;
		this.roleList = roleList;
		this.meetingreservAtionuserId = meetingreservAtionuserId;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeAccount() {
		return employeeAccount;
	}

	public void setEmployeeAccount(String employeeAccount) {
		this.employeeAccount = employeeAccount;
	}

	public String getEmployeePassword() {
		return employeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public Department getDepartment() {
		return Department;
	}

	public void setDepartment(Department department) {
		Department = department;
	}

	public List<Matter> getFiles() {
		return files;
	}

	public void setFiles(List<Matter> files) {
		this.files = files;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

	public List<Meeting> getMeetingreservAtionuserId() {
		return meetingreservAtionuserId;
	}

	public void setMeetingreservAtionuserId(List<Meeting> meetingreservAtionuserId) {
		this.meetingreservAtionuserId = meetingreservAtionuserId;
	}

}
