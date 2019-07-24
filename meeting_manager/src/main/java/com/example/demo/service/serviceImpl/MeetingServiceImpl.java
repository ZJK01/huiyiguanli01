package com.example.demo.service.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MettingDao;
import com.example.demo.entity.Meeting;
import com.example.demo.service.MeetingService;

@Service(value = "MeetingServiceImpl")
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	MettingDao mettingDao;

	@Override
	public Integer count() {
		return (int) mettingDao.count();
	}

	@Override
	public Page<Meeting> fy(Integer pages, Integer pageCount) {
		Pageable pageable = new PageRequest(pages, pageCount);
		Page<Meeting> page = mettingDao.findAll(pageable);
		return page;
	}

	@Override
	public void add(Meeting meeting) {
		mettingDao.save(meeting);
	}

	@Override
	public List<Meeting> findMeeting(String firstMonth, String lastMonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date data1=null;
		Date data2=null;
		try {
			 data1=sdf.parse(firstMonth);
			 data2=sdf.parse(lastMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return  mettingDao.findTime(data1,data2);
	}

}
