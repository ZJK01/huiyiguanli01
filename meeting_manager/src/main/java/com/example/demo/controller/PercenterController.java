package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 会议预定
 * */
@Controller
@RequestMapping("/percenter")
public class PercenterController {

	@GetMapping("/")
	public String index() {
		return "cmeeting/bookmeeting";
	}

}
