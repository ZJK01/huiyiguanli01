package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//	会议室管理跳转
	@GetMapping("/")
	public String indexCroom() {
		return "/croom/croom";
	}

//	会议室添加
	public String addCroom(@ModelAttribute Boradroom boradroom) {
		boradroomService.addCroom(boradroom);
		return "redirect:/croom/lookcroom";
	}

//	查看会议室
	@GetMapping("/lookcroom")
	public String lookCroom(@RequestParam(defaultValue = "0") Integer id, Model model) {
//		List<Boradroom> boradrooms = boradroomService.getCrooms();
		Integer count = boradroomService.getcount();
		Integer pages = 3;
		Integer start;
		if (count % pages == 0) {
			start = count / pages - 1;
		} else {
			start = count / pages;
		}

		Integer one = 0;
		if (0 != id) {
			one = id;
		}
		
		Page<Boradroom> boradrooms = boradroomService.getBrooms(start, pages);
		model.addAttribute("one", one);
		model.addAttribute("start", start);
		model.addAttribute("boradrooms", boradrooms);
		return "/croom/lookcroom";
	}

	@GetMapping("/updatecroom/{bid}")
	public String updateCroom(@PathVariable Integer bid, Model model) {
		Optional<Boradroom> boradroom = boradroomService.getCroom(bid);
		String statu = boradroom.get().getBoradRoomStatus();
		if (boradroom.get().getBoradRoomStatus().equals("启用")) {
			statu = "启用";
		} else {
			statu = "no";
		}
		model.addAttribute("boradroom", boradroom.get());
		model.addAttribute("status", statu);
		return "/croom/croomupdate";
	}

//	更新会议室
	@PostMapping("/updatecroom")
	public String updateCroom01(@ModelAttribute Boradroom boradroom) {
		boradroomService.updateCroom(boradroom);
		return "redirect:/croom/lookcroom";
	}

//	删除会议室
	@GetMapping("/deletecroom/{bid}")
	public String deleteCroom(@PathVariable Integer bid) {
		boradroomService.deleteCroom(bid);
		return "redirect:/croom/lookcroom";
	}

}
