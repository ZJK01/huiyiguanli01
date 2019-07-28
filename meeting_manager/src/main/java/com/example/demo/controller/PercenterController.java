package com.example.demo.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Boradroom;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Meeting;
import com.example.demo.service.BoradroomService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.MeetingService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 会议预定
 * */
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
		Boradroom boradroom;
		// 查询已预订的所有会议
		List<Meeting> meetings = meetingService.findMeetings();
		// 获取需要移除的会议室集合
		List<Boradroom> boradrooms2 = new ArrayList<Boradroom>();
		// 遍历所有会议
		for (Meeting meeting : meetings) {
			int starttime = date1.compareTo(meeting.getStartTime());
			int start = date1.compareTo(meeting.getEndTime());
			int endtime = date2.compareTo(meeting.getEndTime());
			int end = date2.compareTo(meeting.getStartTime());

			// 时间比较，等于1大于比较时间，小于1小于比较时间，等于0时间相等
			if ((starttime == 1 && start == -1) || (starttime == -1 && end == 1)
					|| (starttime == -1 && (end == 1 && endtime == -1))) {
				// 获取时间冲突的会议室对象
				boradroom = boradroomSerivce.getCroom(meeting.getBroomId());
				boradrooms2.add(boradroom);

			}
		}
		boradrooms.removeAll(boradrooms2);
		JSONArray array = JSONArray.fromObject(boradrooms);
		return array.toString();
	}

	@PostMapping("/employee")
	@ResponseBody
	//根据前端选择的部门信息实时返回部门员工数据
	public String ajax03(@RequestParam Integer department) {
		List<Employee> employees = employeeService.findByEmployeeBydepartmentId(department);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray array = JSONArray.fromObject(employees,jsonConfig);
		return array.toString();
	}

	// 首页
	//根据登录的员工的信息获取部门信息
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		Integer did;
//		获取部门信息
		List<Department> departments;
		Employee employee = (Employee) session.getAttribute("Employee");
		String positionname = employee.getRoleList().get(0).getRole();
		System.out.println(positionname);
		if ("经理".equals(positionname)) {
			did = 40;
			departments = departmentService.findAllDepartmentIdNot(did);
		} else {		
			did = employee.getDepartment().getDepartmentId();
			departments = departmentService.findAllDepartmentId(did);
		}
		model.addAttribute("departments", departments);
		return "cmeeting/bookmeeting";
	}
	
	@PostMapping("/meeting")
	@ResponseBody
	public String meeting(@ModelAttribute Meeting meeting,@RequestParam("start") String start,@RequestParam("end") String end,@RequestParam("hidden") String hidden) {
		System.out.println(start);
		System.out.println(end);
		System.out.println(meeting.getMeetingStas());
		System.out.println(meeting.getEmployeeId());
		System.out.println(meeting.getMeetingCount());
		Date date = new Date();
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		meeting.setReservAtionTime(date);
		System.out.println(meeting.getReservAtionTime());
		System.out.println(hidden);
		return "cfghljkfcolgiho";
	}

}
