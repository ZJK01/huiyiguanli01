package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.demo.entity.Boradroom;

public interface BoradroomService {

//	添加会议室
	public void addCroom(Boradroom boradroom);

//	查询会议室
	public List<Boradroom> getCrooms();

//	查询单个会议室
	public Boradroom getCroom(Integer bid);

//	更新会议室信息
	public void updateCroom(Boradroom boradroom);

//	删除会议室
	public void deleteCroom(Integer bid);

//	public Integer getcount();
//
//	public Page<Boradroom> getBrooms(Integer pages, Integer page);
//
//	public List<Boradroom> getBoradrooms(String status);

	
}
