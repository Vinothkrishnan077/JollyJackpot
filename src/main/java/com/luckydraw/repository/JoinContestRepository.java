package com.luckydraw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luckydraw.model.Contest;
import com.luckydraw.model.JoinContest;
import com.luckydraw.model.User;

@Repository
public interface JoinContestRepository extends JpaRepository<JoinContest, Long> {
	int countByContest(Contest contest);

	List<JoinContest> findByUser(User user);

	boolean existsByContestAndUser(Contest contest, User user);

	List<JoinContest> findByContest(Contest contest);
}
