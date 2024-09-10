package com.luckydraw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_contest_list")
public class UserContestList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userContestId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", insertable = false, updatable = false, referencedColumnName = "userId")
	private User user;
	private Long user_id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "admin_id", insertable = false, updatable = false, referencedColumnName = "adminId")
	private Admin admin;
	private Long admin_id;

	public Long getUserContestId() {
		return userContestId;
	}

	public void setUserContestId(Long userContestId) {
		this.userContestId = userContestId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Admin getAdmin() {
		return admin;
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

}
