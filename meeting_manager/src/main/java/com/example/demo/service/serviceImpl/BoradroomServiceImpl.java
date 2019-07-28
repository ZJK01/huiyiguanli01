package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

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

	@Override
//	@Cacheable(cacheNames = "crooms", key = "123")
	public List<Boradroom> getCrooms() {
		// TODO Auto-generated method stub
		return boradroomDao.findAll();
	}

	@Override
//	@Cacheable(cacheNames = "croom", key = "#bid.toString()")
	public Boradroom getCroom(Integer bid) {
		// TODO Auto-generated method stub
		return boradroomDao.findById(bid).get();
	}

	@Override
//	@CachePut(cacheNames = "crooms", key = "123")
	public void updateCroom(Boradroom boradroom) {
		// TODO Auto-generated method stub
		boradroomDao.saveAndFlush(boradroom);
	}

	@Override
	public void deleteCroom(Integer bid) {
		// TODO Auto-generated method stub
		boradroomDao.deleteById(bid);
	}

}
