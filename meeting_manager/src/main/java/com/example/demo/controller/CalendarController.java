package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*日历控制层*/
@Controller
@RequestMapping("calendar")
public class CalendarController {

	@GetMapping("/")
	public String name() {
		return "/calendar/calender";
	}
}
