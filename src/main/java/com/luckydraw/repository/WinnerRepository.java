package com.luckydraw.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luckydraw.model.Contest;
import com.luckydraw.model.User;
import com.luckydraw.model.Winner;

@Repository
public interface WinnerRepository extends JpaRepository<Winner, Long> {

	List<Winner> findByWinningTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

	boolean existsByContest(Contest contest);

	List<Winner> findByUser(User user);

}
