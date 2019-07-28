package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Meeting;
import com.example.demo.entity.SysRole;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.MeetingService;
import com.example.demo.service.SysRoleService;
import com.example.demo.service.serviceImpl.EmployeeServiceImpl;


/*部门管理控制层*/
@Controller
@RequestMapping("/demanager")
public class DemanagerController {

	@Resource(name = "EmployeeServiceImpl")
	private EmployeeServiceImpl employeeServiceImpl;

	@Resource(name = "MeetingServiceImpl")
	private MeetingService meetingService;
	
	@Resource(name="DepartmentServiceImpl")
	private DepartmentService departmentService;
	
	@Resource(name="SysRoleServiceImpl")
	private SysRoleService sysRoleService;
	
	
	//添加部门和职位
	@RequestMapping("/zj")
	public String zj() {
		return "demanager/zj";		
	}

	@GetMapping("/zc")
	public String zcc(Model model) {
		//获得部门
		Integer idInteger=40;
		List<Department> departments=departmentService.findAllDepartmentIdNot(idInteger);
		//获得等级(角色)
		List<SysRole> SysRole = sysRoleService.findAll();
	
		model.addAttribute("departments",departments);
		model.addAttribute("sysrole",SysRole);
		return "/demanager/reg";
	}
	

	@PostMapping("httt")
	public String htt() {
		return "/demanager/ht";
	}

	// 分页(员工)
	@GetMapping("/pagefy/{id}")
	public String huiyi(@RequestParam(defaultValue = "0",name="id") Integer id,Model model) {

		Integer count = employeeServiceImpl.count();// 总行数
		Integer pages; // 总页数
		Integer pageCount = 3; // 每页浏览行数
		if (count % pageCount == 0) {
			pages = count / pageCount;
		} else {
			pages = count / pageCount + 1;
		}
		Integer pagecurrent = 0; // 当前页数
		if (0 != id) {
			pagecurrent = id; // 当前页数=id
		}
		Page<Employee> list = employeeServiceImpl.fy(pagecurrent, pageCount);
		model.addAttribute("list", list);
		model.addAttribute("pagecurrent", pagecurrent);
		model.addAttribute("pages", pages);

		return "/demanager/pagefy";

	}

	// 登陆进入预约页面
	@PostMapping("/yuyue")
	public String managerlogin(HttpServletRequest request, Model model, @ModelAttribute Employee employee,
			HttpSession session) {
		String error = "账号或者密码错误";
		session.setAttribute("user1", employee);
		if (null != employee) {
			model.addAttribute("user1", employee);
			return "/demanager/yuyue";
		} else {
			model.addAttribute("error", error);
			return "/demanager/login";

		}
	}

	// 注册用户
	@PostMapping("reg")
	public String adduser(@ModelAttribute Employee employee,String departMent,String SysRole,HttpSession session, Model model) {
		if(employee!=null) {
			Department department2=departmentService.findDeptnoId(departMent);
			employee.setDepartment(department2);  //给与部门
			
			SysRole sysRole2=sysRoleService.findByid(Integer.parseInt(SysRole));
			List<SysRole> roleList=new ArrayList<SysRole>();
			roleList.add(sysRole2);
			employee.setRoleList(roleList);		  //给与角色
			
			//设置加密规则(shiro)
			employee.setEmployeePassword((new SimpleHash("MD5",employee.getEmployeePassword(),ByteSource.Util.bytes("123"),1024)).toString());
			employeeServiceImpl.add(employee);
			return "redirect:/demanager/pagefy/0";
		}else {
			return "redirect:zc";
		}
	}
	
	//错误界面(403)
	@GetMapping("/403")
	public String  errorpage() {
		return "/403";
	}
	
	
	

	// 预定会议
	@PostMapping("/ht1")
	public String ht(@ModelAttribute Meeting meeting, Model model) {
		if (meeting.getReservAtionTime() !=null) {
			if (meeting.getStartTime() !=null) {
				if (meeting.getBroomId() !=null) {
					if (meeting.getMeetingStas() != "") {
						if (meeting.getEndTime()!=null) {
							meetingService.add(meeting);
							return "/demanager/sucess_send";
						} else {
							return "redirect:httt";
						}
					} else {
						return "redirect:httt";
					}
				} else {
					return "redirect:httt";
				}
			} else {
				return "redirect:httt";
			}
		} else {
			return "redirect:httt";
		}

	}
	
	//管理部门所有人员
		@GetMapping("/department/{id}")
		public String department(@RequestParam(defaultValue = "0") String id,Model model) {
			Integer count = departmentService.count();// 总行数
			Integer pages; // 总页数
			Integer pageCount = 3; // 每页浏览行数
			 Integer idd= Integer.valueOf(id);
			if (count % pageCount == 0) {
				pages = count / pageCount-1;
			} else {
				pages = count / pageCount;
			}
			Integer pageCurrent = 0; // 当前页数
			if (0 != idd) {
				pageCurrent = idd; // 当前页数=id
			}		
			Page<Department> dp2 = departmentService.fy(pageCurrent, pageCount);
			model.addAttribute("dp2", dp2);
			model.addAttribute("pageCurrent", pageCurrent);
			model.addAttribute("pages", pages);		
			return "demanager/department";
		}
	

	//查询部门	
    @GetMapping("/department/like")
	public String like(HttpSession session,Department dp) {      
		if(dp.getWorkSet().trim()!="") {
			List<Department> dp1= departmentService.like(((Department) dp).getWorkSet());	    	 
	    	session.setAttribute("dp2", dp1);
			return "/demanager/like";
		}else {
			return "redirect:/demanager/department/id=0";
		}
	}
	
  //添加部门和职位
  	@GetMapping("save")
  	public String save(Department dp,HttpSession session) {	
  		if(dp.getDepartMentName()!="") {
  			if(dp.getWorkSet()!="") {
  				departmentService.save(dp);
  				return "redirect:./department/0";
  			}else {							 
  				return "redirect:zj";
  			}
  			}else {
  				return "redirect:zj";
  			}

  	}
  	
  	//删除部门
  	@GetMapping("/deldeptno/{id}") 
  	public String deldeptno(@RequestParam String id) {
  		Integer new_idInteger=Integer.parseInt(id);
  		departmentService.deleteById(new_idInteger);
  		return "redirect:/demanager/department/id";
  	}
  	
  	
  	//删除员工
  	@GetMapping("/del/{id}")
  	public String del(@RequestParam String id) {
  		employeeServiceImpl.delEmployee(Integer.parseInt(id));
  		return "redirect:/demanager/pagefy/0";
  	}

}



