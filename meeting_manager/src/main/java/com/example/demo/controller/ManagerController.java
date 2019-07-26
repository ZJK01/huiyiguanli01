package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;
import com.example.demo.entity.Postition;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.PostitionService;

import net.sf.json.JSONArray;

/*管理员控制层*/
@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Resource(name = "ManagerServiceImpl")
	private ManagerService managerService;

	@Resource(name = "EmployeeServiceImpl")
	private EmployeeService employeeService;

	@Resource(name="PostitionServiceImpl")
	private PostitionService postitionService;
	
	@Resource(name="DepartmentServiceImpl")
	private DepartmentService deparmentService;

	@GetMapping(value= { "/", "/index"})
	public String main(HttpSession session) {
		// 销毁session
		session.invalidate();
		return "index";
	}


	@PostMapping("yuyue")
	public String managerlogin(String username, String password, HttpSession session) {
		Manager manager = new Manager();
		manager.setManagerName(username);
		manager.setManagerPassword(password);
		Manager man = managerService.search(manager);
		if (man != null) {
			session.removeAttribute("Emplpyee");
			session.setAttribute("Manager", man);
			return "/yuyue";
		} else {
			Employee employee = new Employee(username, password);
			Employee emp = employeeService.findByEmployeeNameAndEmployeePassword(employee);
			if (emp != null) {
				session.removeAttribute("Manager");
				session.setAttribute("Employee", emp);
				return "/yuyue";
			} else {
				return "/index";
			}
		}
	}
	
	
	/**
	 * 取得职位
	 * */
	@GetMapping("getpostition")
	@ResponseBody
	public String getpostition() {
		List<Postition> Postition=postitionService.findAll();
		
		JSONArray jsonArray=JSONArray.fromObject(Postition);
		return jsonArray.toString();
	}
	
	/**
	 * 部门
	 * */
	@GetMapping("getdepartment")
	@ResponseBody
	public String getdepartment() {
		String idInteger="40";
		List<Department> Postition=deparmentService.findAllDepartmentIdNot(idInteger);
		
		JSONArray jsonArray=JSONArray.fromObject(Postition);
		return jsonArray.toString();
	}
}
