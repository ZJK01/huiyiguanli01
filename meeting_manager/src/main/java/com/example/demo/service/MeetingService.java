package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.entity.Meeting;

public interface MeetingService {

	
	public void add(Meeting meeting);

	public Integer count();

	public Page<Meeting> fy(Integer pages, Integer pageCount);
		
	public List<Meeting> findMeetings();

	public List<Meeting> findMeeting(String firstMonth, String lastMonth);
	
}
