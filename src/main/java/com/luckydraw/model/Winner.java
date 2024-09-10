package com.luckydraw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Winner {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "contest_id")
	private Contest contest;

	private LocalDateTime winningTime;

	public Winner() {
	}

	public Winner(User user, Contest contest, LocalDateTime winningTime) {
		this.user = user;
		this.contest = contest;
		this.winningTime = winningTime;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Contest getContest() {
		return contest;
	}

	public LocalDateTime getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(LocalDateTime winningTime) {
		this.winningTime = winningTime;
	}
}
