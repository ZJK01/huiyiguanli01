package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Boradroom;

/*
 * 会议室：添加，查看
 * */
@Controller
@RequestMapping("croom")
public class CRoomController {

//	会议室添加
	@GetMapping("/")
	public String indexCroom() {
		return "/croom/croom";
	}

	@PostMapping
	public String addCroom(@ModelAttribute Boradroom boradroom) {
		
		return "/croom/croom";
	}

}
