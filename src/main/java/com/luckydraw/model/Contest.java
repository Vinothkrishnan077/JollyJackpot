package com.luckydraw.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "contest")
public class Contest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long contestId;
	private String ContestName;
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// private String startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp postTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private String endTime;
	private Double seats;
	private String status;
	// @JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "admin_id", insertable = false, updatable = false, referencedColumnName = "adminId")
	private Admin admin;
	private Long admin_id;

	public Long getContestId() {
		return contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	// public String getStartTime() {
	// return startTime;
	// }
	//
	// public void setStartTime(String startTime) {
	// this.startTime = startTime;
	// }

	public Admin getAdmin() {
		return admin;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Long getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}

	public String getContestName() {
		return ContestName;
	}

	public void setContestName(String contestName) {
		ContestName = contestName;
	}

	public Double getSeats() {
		return seats;
	}

	public void setSeats(Double seats) {
		this.seats = seats;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
