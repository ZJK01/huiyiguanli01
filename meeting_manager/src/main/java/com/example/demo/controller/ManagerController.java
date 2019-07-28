package com.example.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Employee;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.SysRoleService;

/*管理员控制层*/
@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Resource(name = "EmployeeServiceImpl")
	private EmployeeService employeeService;

	@Resource(name = "SysRoleServiceImpl")
	private SysRoleService SysRoleService;

	@Resource(name = "DepartmentServiceImpl")
	private DepartmentService deparmentService;

	@GetMapping(value = { "/", "/index" })
	public String main(HttpSession session,HttpServletRequest request) {
		// 销毁session
		/* session.invalidate(); */
		session.removeAttribute("Employee");	//上面总是报错就该为这种样子了(不推荐这种写法,因为如果项目太大会有影响)
		return "index";
	}

	@PostMapping("yuyue")
	public String managerlogin(String username, String password, HttpSession session, ModelMap model) {

		// 1.获取subject
		Subject subject = SecurityUtils.getSubject();
		// 2.封装数据
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// 3.执行登录方法(判断)
		try {
			System.out.println(password);
			subject.login(token);
			Employee employee = (Employee) subject.getPrincipal();
			session.setAttribute("Employee", employee);
			return "/yuyue";
		} catch (UnknownAccountException e) {
			model.addAttribute("msg", "用户名不存在");
			return "/index";
		} catch (IncorrectCredentialsException e) {
			model.addAttribute("msg", "密码错误");
			return "/index";
		}

	}

	/**
	 * 取得职位
	 */
	/*
	 * @GetMapping("/getSysRole")
	 * 
	 * @ResponseBody public String getpostition() { List<SysRole> SysRole =
	 * SysRoleService.findAll(); JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
	 * jsonConfig.setExcludes(new String[] { "permissions", "employees"}); //
	 * 此处是亮点，只要将所需忽略字段加到数组中即可 jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
	 * jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	 * 
	 * JSONArray jsonArray = JSONArray.fromObject(SysRole); return
	 * jsonArray.toString(); }
	 */

}
