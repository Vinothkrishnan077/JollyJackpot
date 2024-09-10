package com.luckydraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luckydraw.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByEmail(String email);

	Admin findByPhoneNumber(String phoneNumber);

}
