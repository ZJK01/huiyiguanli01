package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.SysRole;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.SysRoleService;

import net.sf.json.JSONArray;

/*管理员控制层*/
@Controller
@RequestMapping("/manager")
public class ManagerController {


	@Resource(name = "EmployeeServiceImpl")
	private EmployeeService employeeService;
	
	@Resource(name="SysRoleServiceImpl")
	private SysRoleService SysRoleService;
	
	@Resource(name="DepartmentServiceImpl")
	private DepartmentService deparmentService;

	@GetMapping(value= { "/", "/index"})
	public String main(HttpSession session) {
		// 销毁session
		session.invalidate();
		return "index";
	}

	@PostMapping("yuyue")
	public String managerlogin(String username, String password, HttpSession session,ModelMap model) {
		
		//1.获取subject
		Subject subject=SecurityUtils.getSubject();
		//2.封装数据
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		//3.执行登录方法(判断)
		try {
			subject.login(token);
			Employee employee=(Employee) subject.getPrincipal();
			session.setAttribute("Employee",employee);
			return "/yuyue";
		} catch (UnknownAccountException e) {
			model.addAttribute("msg","用户名不存在");
			return "/index";
		}catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", "密码错误");
			return "/index";
		}
	
	}
	
	
	/**
	 * 取得职位
	 * */
	@GetMapping("getSysRole")
	@ResponseBody
	public String getpostition() {
		List<SysRole> Postition=SysRoleService.findAll();
		
		JSONArray jsonArray=JSONArray.fromObject(Postition);
		return jsonArray.toString();
	}
	
	/**
	 * 部门
	 * */
	@GetMapping("getdepartment")
	@ResponseBody
	public String getdepartment() {
		Integer idInteger=40;
		List<Department> Postition=deparmentService.findAllDepartmentIdNot(idInteger);
		
		JSONArray jsonArray=JSONArray.fromObject(Postition);
		return jsonArray.toString();
	}
	
	
}

