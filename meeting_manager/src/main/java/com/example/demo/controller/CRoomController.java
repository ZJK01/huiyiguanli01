package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Boradroom;
import com.example.demo.service.BoradroomService;

/*
 * 会议室：添加，查看
 * */
@Controller
@RequestMapping("croom")
public class CRoomController {

	@Autowired
	private BoradroomService boradroomService;

//	会议室添加
	@GetMapping("/")
	public String indexCroom() {
		return "/croom/croom";
	}

	@PostMapping("/addroom")
	public String addCroom(@ModelAttribute Boradroom boradroom) {
		boradroomService.addCroom(boradroom);
		return "/croom/croom";
	}

}
