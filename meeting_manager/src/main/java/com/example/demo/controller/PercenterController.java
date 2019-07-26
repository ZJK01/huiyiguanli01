package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Boradroom;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;
import com.example.demo.entity.Meeting;
import com.example.demo.service.BoradroomService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.MeetingService;
import com.mysql.cj.xdevapi.JsonArray;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/percenter")
public class PercenterController {

	@Autowired
	//会议室
	private BoradroomService boradroomSerivce;

	@Autowired
	//员工
	private EmployeeService employeeService;

	@Autowired
	//部门
	private DepartmentService departmentService;

	@Autowired
	//会议
	private MeetingService meetingService;

	@PostMapping("/meetingroom")
	@ResponseBody
	//根据输入的会议预订时间实时返回空闲的会议室
	public String ajax01(@RequestParam String time1, @RequestParam String time2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		// 将字符串时间格式化成date对象
		try {
			date1 = df.parse(time1);
			date2 = df.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String status = "启用";
		// 获取所有启用状态的会议室
		List<Boradroom> boradrooms = boradroomSerivce.getBoradrooms(status);
		Optional<Boradroom> boradroom;
		// 查询已预订的所有会议
		List<Meeting> meetings = meetingService.findMeetings();
		// 获取需要移除的会议室集合
		List<Boradroom> boradrooms2 = new ArrayList<Boradroom>();
		// 遍历所有会议
		for (Meeting meeting : meetings) {
			int starttime = date1.compareTo(meeting.getStarttime());
			int start = date1.compareTo(meeting.getEndtime());
			int endtime = date2.compareTo(meeting.getEndtime());
			int end = date2.compareTo(meeting.getStarttime());

			// 时间比较，等于1大于比较时间，小于1小于比较时间，等于0时间相等
			if ((starttime == 1 && start == -1) || (starttime == -1 && end == 1)
					|| (starttime == -1 && (end == 1 && endtime == -1))) {
				// 获取时间冲突的会议室对象
				boradroom = boradroomSerivce.getCroom(meeting.getBroomid());
				boradrooms2.add(boradroom.get());

			}
		}
		boradrooms.removeAll(boradrooms2);
		JSONArray array = JSONArray.fromObject(boradrooms);
		return array.toString();
	}

	@PostMapping("/employee")
	@ResponseBody
	//根据前端选择的部门信息实时返回部门员工数据
	public String ajax03(@RequestParam String department) {
		List<Employee> employees = employeeService.findAllByDepatment(department);
		JSONArray array = JSONArray.fromObject(employees);
		return array.toString();
	}

	// 首页
	//根据登录的员工的信息获取部门信息
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		String did;
//		获取部门信息
		List<Department> departments;
		if (null != session.getAttribute("Manager")) {
			did = "40";
			departments = departmentService.findallByDepartmentidNot(did);
		} else {
			Employee employee = (Employee) session.getAttribute("Employee");
			did = employee.getDepartMent();
			departments = departmentService.findallByDepartmentid(did);
		}
		model.addAttribute("departments", departments);
		return "cmeeting/bookmeeting";
	}

}
