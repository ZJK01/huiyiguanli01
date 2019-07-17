package com.example.demo.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.dao.BoradroomDao;
import com.example.demo.entity.Boradroom;
import com.example.demo.service.BoradroomService;

@Service(value = "BoradroomServiceImpl")
public class BoradroomServiceImpl implements BoradroomService {

	@Resource
	private BoradroomDao boradroomDao;

	@Override
	public void addCroom(Boradroom boradroom) {
		// TODO Auto-generated method stub
		boradroomDao.save(boradroom);
	}

}
