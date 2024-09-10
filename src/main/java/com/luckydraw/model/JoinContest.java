package com.luckydraw.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JoinContest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long joinId;
	private String couponCode;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "userId")
	@JsonManagedReference
	private User user;
	private Long user_id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contest_id", insertable = false, updatable = false, referencedColumnName = "contestId")
	@JsonManagedReference
	private Contest contest;
	private Long contest_id;

	public Long getJoinId() {
		return joinId;
	}

	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getContest_id() {
		return contest_id;
	}

	public void setContest_id(Long contest_id) {
		this.contest_id = contest_id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public Long getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(Long user_id) {
//		this.user_id = user_id;
//	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

//	public Long getContest_id() {
//		return contest_id;
//	}
//
//	public void setContest_id(Long contest_id) {
//		this.contest_id = contest_id;
//	}

	// Additional getters for contestId and userId
	public Long getContestId() {
		return contest != null ? contest.getContestId() : null;
	}

	public Long getUserId() {
		return user != null ? user.getUserId() : null;
	}

}
