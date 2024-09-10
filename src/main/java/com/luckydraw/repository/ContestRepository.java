package com.luckydraw.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luckydraw.model.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
	List<Contest> findByPostTimeBeforeAndStatus(Timestamp now, String status);

	List<Contest> findByEndTimeBeforeAndStatusIn(String endTime, List<String> statuses);

//	List<Contest> findByEndTimeBeforeAndStatus(String endTime, String status);
}
