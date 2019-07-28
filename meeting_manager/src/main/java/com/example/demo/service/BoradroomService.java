package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.example.demo.common.util.RedisUtil;
import com.example.demo.entity.Boradroom;

public interface BoradroomService {

//	添加会议室
	public void addCroom(Boradroom boradroom);

//	查询会议室
	public List<Boradroom> getCrooms();
	
//	根据会议室状态查询
	public List<Boradroom> getBoradrooms(String status);

//	查询单个会议室
	public Boradroom getCroom(Integer bid);

//	更新会议室信息
	public void updateCroom(Boradroom boradroom);

//	删除会议室
	public void deleteCroom(Integer bid);
		
//	分页
	public Page<Boradroom> getBrooms(Integer pages,Integer page);
	
	public Integer getcount();
}
