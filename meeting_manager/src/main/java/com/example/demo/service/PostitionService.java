package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Postition;

public interface PostitionService {

	public List<Postition> findAll();
	
	public Optional<Postition> findById(Integer positioniId);
	
}
