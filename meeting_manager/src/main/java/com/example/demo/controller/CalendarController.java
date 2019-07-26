package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Meeting;
import com.example.demo.service.MeetingService;

import net.sf.json.JSONArray;

/*日历控制层*/
@Controller
@RequestMapping("/calendar")
public class CalendarController {

	@Autowired
	MeetingService meetingService; // 会议
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@GetMapping("/")
	public String name(Model map) {
		Calendar calendar  = Calendar.getInstance();  
		//本月第一天
		calendar.set(Calendar.DAY_OF_MONTH, 1);  
		//将小时至0  
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		//将分钟至0  
		calendar.set(Calendar.MINUTE, 0);  
		//将秒至0  
		calendar.set(Calendar.SECOND,0);  
		//将毫秒至0  
		calendar.set(Calendar.MILLISECOND, 0);  
		//获得当前月第一天  
		String  firstday=format.format(calendar.getTime());  
		//将当前月加1
		calendar.add(Calendar.MONTH, 1);  
		//在当前月的下一月基础上减去1毫秒  
		calendar.add(Calendar.MILLISECOND, -1);  
		//获得当前月最后一天  
		String lastday=format.format(calendar.getTime());  
		List<Meeting> lists = meetingService.findMeeting(firstday,lastday);
		map.addAttribute("lists",lists);
		
		return "/calendar/calender";
	}
	
	@PostMapping("/flush")
	@ResponseBody
	public String flush(@RequestParam(defaultValue="0") String year,@RequestParam(defaultValue="0") String month) {
		Calendar calendar  = Calendar.getInstance();  
		//年
		if(Integer.parseInt(year)!=0) {
			calendar.set(Calendar.YEAR,Integer.parseInt(year)); 
		}
		//月（月份0代表1月）  
		if(Integer.parseInt(month)!=0) {
			calendar.set(Calendar.MONTH, Integer.parseInt(month)-1); 
		}
		//本月第一天
		calendar.set(Calendar.DAY_OF_MONTH, 1);  
		//将小时至0  
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		//将分钟至0  
		calendar.set(Calendar.MINUTE, 0);  
		//将秒至0  
		calendar.set(Calendar.SECOND,0);  
		//将毫秒至0  
		calendar.set(Calendar.MILLISECOND, 0);  
		//获得当前月第一天  
		String  firstday=format.format(calendar.getTime());  
		//将当前月加1
		calendar.add(Calendar.MONTH, 1);  
		//在当前月的下一月基础上减去1毫秒  
		calendar.add(Calendar.MILLISECOND, -1);  
		//获得当前月最后一天  
		String lastday=format.format(calendar.getTime());  
		List<Meeting> lists = meetingService.findMeeting(firstday,lastday);
		
		JSONArray jsonArray=JSONArray.fromObject(lists);
		
		System.out.println(jsonArray);
		return jsonArray.toString();
	}
	

}
