package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Meeting;
import com.example.demo.service.MeetingService;

/*日历控制层*/
@Controller
@RequestMapping("calendar")
public class CalendarController {

	@Autowired
	MeetingService meetingService; // 会议

	@GetMapping("/")
	@ResponseBody
	public String name(ModelMap map) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();  
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
		/*
		 * for (Meeting meeting : lists) { System.out.println(meeting.toString()); }
		 */
		map.addAttribute("lists",lists);
		
		return "/calendar/calender";
	}

}
