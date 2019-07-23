package com.example.demo.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.common.util.Timeformat;
import com.example.demo.entity.Meeting;
import com.example.demo.service.MeetingService;

/*日历控制层*/
@Controller
@RequestMapping("calendar")
public class CalendarController {
	
	@Autowired
	MeetingService meetingService;			//会议
	
	@GetMapping("/")
	public String name(ModelMap map) {
		Calendar cal = Calendar.getInstance();
		/*
		 * System.out.println(cal.YEAR); System.out.println(cal.MONTH+1);
		 */
		String firstMonth=Timeformat.getFirstDayOfMonth( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
		String lastMonth=Timeformat.getFirstDayOfMonth( cal.get(Calendar.YEAR),cal.get(Calendar.MONTH));
		
		List<Meeting> lists=meetingService.findMeeting(firstMonth,lastMonth);
		for(Meeting meeting:lists) {
			System.out.println(meeting.toString());
		}
		
		return "/calendar/calender";
	}
	
	
}
