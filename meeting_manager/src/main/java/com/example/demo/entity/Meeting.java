package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * 会议表
 */
@Entity
public class Meeting implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer meetingId; // 会议id
	private Integer broomId; // 会议室id
	private Integer meetingCount; // 会议人数
	private Date startTime; // 会议开始时间
	private Date endTime; // 会议结束时间
	private String meetingStas; // 会议内容
	private Date reservAtionTime; // 预定时间

	@ManyToOne
	@JoinColumn(name = "employeeId")
	private Employee employeeId;
	
	@ManyToMany
    @JoinTable(name="boradroom_meeting",joinColumns={@JoinColumn(name="meeting_id",referencedColumnName="meetingId")},inverseJoinColumns={@JoinColumn(name="boradroom",referencedColumnName="boradroomId")})
	private List<Boradroom> boradrooms;
	

	public Integer getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}

	public Integer getBroomId() {
		return broomId;
	}

	public void setBroomId(Integer broomId) {
		this.broomId = broomId;
	}

	public Integer getMeetingCount() {
		return meetingCount;
	}

	public void setMeetingCount(Integer meetingCount) {
		this.meetingCount = meetingCount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMeetingStas() {
		return meetingStas;
	}

	public void setMeetingStas(String meetingStas) {
		this.meetingStas = meetingStas;
	}

	public Date getReservAtionTime() {
		return reservAtionTime;
	}

	public void setReservAtionTime(Date reservAtionTime) {
		this.reservAtionTime = reservAtionTime;
	}

	
	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}


	public Meeting(Integer meetingId, Integer broomId, Integer meetingCount, Date startTime, Date endTime,
			String meetingStas, Date reservAtionTime, Employee employeeId) {
		super();
		this.meetingId = meetingId;
		this.broomId = broomId;
		this.meetingCount = meetingCount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.meetingStas = meetingStas;
		this.reservAtionTime = reservAtionTime;
		this.employeeId = employeeId;
	}

	public Meeting() {
		super();
	}

}
