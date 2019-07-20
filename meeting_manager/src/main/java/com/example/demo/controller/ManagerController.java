package com.example.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ManagerService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Resource(name="ManagerServiceImpl")
	private ManagerService managerService;
	
	@Resource(name="EmployeeServiceImpl")
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String main(HttpSession session) {
		// 销毁session
		session.invalidate();
		return "index";
	}
	
	@PostMapping("/yuyue")
	public String managerlogin(String username,String password,HttpSession session) {
		Manager manager=new Manager();
		manager.setManagerName(username);
		manager.setManagerPassword(password);
		Manager man=managerService.search(manager);
		if(man!=null) {
			session.removeAttribute("Emplpyee");
			session.setAttribute("Manager",man);
			return "/yuyue";
		}else {
			Employee employee=new Employee(username,password);
			Employee emp=employeeService.findByEmployeeNameAndEmployeePassword(employee);
			if(emp!=null) {
				session.removeAttribute("Manager");
				session.setAttribute("Employee",emp);
				return "/yuyue";
			}else {
				return "/index";
			}
			
		}
		
	}
		
}	
