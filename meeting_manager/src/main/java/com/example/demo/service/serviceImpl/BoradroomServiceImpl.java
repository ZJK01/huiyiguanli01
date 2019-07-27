package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.common.util.RedisUtil;
import com.example.demo.dao.BoradroomDao;
import com.example.demo.entity.Boradroom;
import com.example.demo.service.BoradroomService;

@Service(value = "BoradroomServiceImpl")
public class BoradroomServiceImpl implements BoradroomService {
	
	@Autowired
	private RedisUtil redisUtil;

	@Resource
	private BoradroomDao boradroomDao;

	@Override
	public void addCroom(Boradroom boradroom) {
		// TODO Auto-generated method stub
		boradroomDao.save(boradroom);
	}

	@Override
	public List<Boradroom> getCrooms() {
		// TODO Auto-generated method stub
		return boradroomDao.findAll();
	}

	@Override
//	@Cacheable(cacheNames = "croom", key = "#bid.toString()")
	public Optional<Boradroom> getCroom(Integer bid) {
		// TODO Auto-generated method stub
		return boradroomDao.findById(bid);
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

	@Override
	public Page<Boradroom> getBrooms(Integer pages, Integer page) {
		// TODO Auto-generated method stub
		@SuppressWarnings("deprecation")
		Pageable pg = new PageRequest(pages, page);
		Page<Boradroom> bPage = boradroomDao.findAll(pg);
		return bPage;
	}

	@Override
	public Integer getcount() {
		// TODO Auto-generated method stub
		return (int) boradroomDao.count();
	}

	@Override
	public List<Boradroom> getBoradrooms(String status) {
		// TODO Auto-generated method stub
		return boradroomDao.findAllByboradRoomStatus(status);
	}

}
