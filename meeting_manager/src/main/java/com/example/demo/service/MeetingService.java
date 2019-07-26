package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.entity.Meeting;

public interface MeetingService {

	public Integer count();

	public Page<Meeting> fy(Integer pages, Integer pageCount);
	
	public void add(Meeting meeting);
	
	public List<Meeting> findMeetings();

}
