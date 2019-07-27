package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PositionDao;
import com.example.demo.entity.Postition;
import com.example.demo.service.PostitionService;

@Service(value="PostitionServiceImpl")
public class PostitionServiceImpl implements PostitionService  {
	
	@Autowired
	PositionDao PositionDao;
	
	@Override
	public List<Postition> findAll() {
		return PositionDao.findAll();
	}

	@Override
	public Optional<Postition> findById(Integer positioniId) {
		// TODO Auto-generated method stub
		return PositionDao.findById(positioniId);
	}
	
}
