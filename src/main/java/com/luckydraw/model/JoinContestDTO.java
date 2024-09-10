package com.luckydraw.model;

public class JoinContestDTO {
	private Long joinId;
	private Long userId;
	private String couponCode;

	public Long getJoinId() {
		return joinId;
	}

	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

}
