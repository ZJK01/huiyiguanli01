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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


import com.example.demo.common.util.RedisUtil;
import com.example.demo.dao.BoradroomDao;
import com.example.demo.entity.Boradroom;
import com.example.demo.service.BoradroomService;

import javassist.expr.NewArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
//		String key = "crooms";
//		Boolean haskey = redisUtil.hasKey(key);
//		System.out.println(1113);
//		if (haskey) {
//			System.out.println(1114);
//			JSONArray array = (JSONArray) redisUtil.lGet(key, 0, -1);
//			@SuppressWarnings({ "deprecation", "unchecked", "static-access" })
//			List<Boradroom> boradrooms = array.toList(array, Boradroom.class);
//			return boradrooms;
//		}else {
//			JSONArray array = JSONArray.fromObject(boradroomDao.findAll());
//			redisUtil.lSet(key, array);
//			System.out.println(1112);
			return boradroomDao.findAll();
//		} 
		
	}

	@Override
//	@Cacheable(cacheNames = "croom", key = "#bid.toString()")
	public Boradroom getCroom(Integer bid) {
		// TODO Auto-generated method stub

//		String key = "crooms";
//		Boolean haskey = redisUtil.hasKey(key);
//		if (haskey) {
//			JSONArray array = (JSONArray) redisUtil.lGet(key, 0, -1);
//			@SuppressWarnings({ "deprecation", "unchecked", "static-access" })
//			List<Boradroom> boradrooms = array.toList(array, Boradroom.class);
//			return boradrooms.get(boradrooms.indexOf(boradroomDao.findById(bid)));
//			
//		}
		return boradroomDao.findById(bid).get();
	}

	@Override
//	@CachePut(cacheNames = "crooms", key = "123")
	public void updateCroom(Boradroom boradroom) {
		// TODO Auto-generated method stub
		boradroomDao.saveAndFlush(boradroom);
//		String key = "crooms";
//		Boolean haskey = redisUtil.hasKey(key);
//		if (haskey) {
//			JSONArray array = (JSONArray) redisUtil.lGet(key, 0, -1);
//			@SuppressWarnings({ "deprecation", "unchecked", "static-access" })
//			List<Boradroom> boradrooms = array.toList(array, Boradroom.class);
//			long index= boradrooms.indexOf(boradroom);
//			redisUtil.lUpdateIndex(key, index, boradroom);	
//		}
		
	}

	@Override
	public void deleteCroom(Integer bid) {
		// TODO Auto-generated method stub
		boradroomDao.deleteById(bid);
//		String key = "crooms";
//		Boolean haskey = redisUtil.hasKey(key);
//		if (haskey) {
//			redisUtil.del(key);	
//		}
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
//		String key = "crooms1";
//		Boolean haskey = redisUtil.hasKey(key);
//		if (haskey) {
//			List<Object> boradrooms = redisUtil.lGet(key, 0, -1);
//
////			@SuppressWarnings({ "deprecation", "unchecked", "static-access" })
//			JSONArray array = (JSONArray) redisUtil.lGet(key, 0, -1);
//			System.out.println(array+"115");
//			List<Boradroom> boradrooms1 = array.toList(array,new Boradroom(),new JsonConfig());
//			return boradrooms1;
//		}else {
////			JSONArray array = JSONArray.fromObject(boradroomDao.findAllByboradRoomStatus(status));
//			redisUtil.lSet(key, boradroomDao.findAllByboradRoomStatus(status));
			return boradroomDao.findAllByboradRoomStatus(status);
//		}
	}

}
