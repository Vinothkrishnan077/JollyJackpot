package com.luckydraw.model;

import java.sql.Timestamp;

public class ContestDTO {
	private Long contestId;
	private String contestName; // Example field
	private Long admin_id;
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private String startTime;
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp postTime;
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String endTime;
	// private Double seats;
	private String status;
	private Double availableSeats;
	private Integer participantsCount;
//	private Set<JoinContestDTO> participants;

	public Long getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}

//	public String getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}

	// public Double getSeats() {
	// 	return seats;
	// }

	// public void setSeats(Double seats) {
	// 	this.seats = seats;
	// }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getContestId() {
		return contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	public String getContestName() {
		return contestName;
	}

	public void setContestName(String contestName) {
		this.contestName = contestName;
	}

	public Double getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Double availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Integer getParticipantsCount() {
		return participantsCount;
	}

	public void setParticipantsCount(Integer participantsCount) {
		this.participantsCount = participantsCount;
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

//	public Set<JoinContestDTO> getParticipants() {
//		return participants;
//	}

//	public void setParticipants(Set<JoinContestDTO> participants) {
//		this.participants = participants;
//	}

}
