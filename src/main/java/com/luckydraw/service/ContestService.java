package com.luckydraw.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.luckydraw.model.Contest;
import com.luckydraw.model.ContestDTO;
import com.luckydraw.model.JoinContest;
import com.luckydraw.model.User;
import com.luckydraw.model.Winner;
import com.luckydraw.repository.ContestRepository;
import com.luckydraw.repository.JoinContestRepository;
import com.luckydraw.repository.UserRepository;
import com.luckydraw.repository.WinnerRepository;

import jakarta.transaction.Transactional;

@Service
public class ContestService {
	@Autowired
	private ContestRepository contestRepository;

	@Autowired
	private JoinContestRepository joinContestRepository;

	@Autowired
	private WinnerRepository winnerRepository;

	@Autowired
	private UserRepository userRepository;

	// private static final Logger logger =
	// LoggerFactory.getLogger(ContestService.class);

	public List<ContestDTO> list() {
		// Fetch entities
		List<Contest> contests = contestRepository.findAll();

		// Convert entities to DTOs
		return contests.stream().filter(contest -> !"completed".equals(contest.getStatus())) // Filter out completed //
																								// // contests
				.map(contest -> {
					ContestDTO dto = new ContestDTO();
					dto.setContestId(contest.getContestId());
					dto.setContestName(contest.getContestName());
					dto.setAdmin_id(contest.getAdmin_id());
					// dto.setStartTime(contest.getStartTime());
					dto.setEndTime(contest.getEndTime());
					dto.setPostTime(contest.getPostTime());
					// dto.setSeats(contest.getSeats());
					dto.setStatus(contest.getStatus());
					// Calculate participants count and available seats
					int participantsCount = joinContestRepository.countByContest(contest);
					// double availableSeats = contest.getSeats() - participantsCount;
					dto.setParticipantsCount(participantsCount);
					// dto.setAvailableSeats(availableSeats);
					return dto;
				}).collect(Collectors.toList());
	}

	public Contest get(Long contestId) {
		return contestRepository.findById(contestId).get();
	}

	public List<Contest> searchall(Contest contest) {
		return contestRepository.findAll(Example.of(contest));
	}

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Transactional
	public Contest saveAll(Contest contest) {
		contest.setPostTime(new Timestamp(System.currentTimeMillis()));
		contest.setSeats((double) 0);
		contest.setStatus("Open");
		return contestRepository.save(contest);
	}

	@Scheduled(fixedRate = 6000)
	public void checkAndCloseContests() {
		LocalDateTime now = LocalDateTime.now();

		// Fetch contests that are either "Full" or "Open" and have an end time before
		// the current time
		List<Contest> contestsToClose = contestRepository
				.findByEndTimeBeforeAndStatusIn(now.format(DATE_TIME_FORMATTER), Arrays.asList("Full", "Open"));

		for (Contest contest : contestsToClose) {
			contest.setStatus("Closed");
			contestRepository.save(contest);
		}
	}

	public Contest updateall(Contest contest) {
		return contestRepository.saveAndFlush(contest);
	}

	public String joinContest(Long contestId, Long userId, String couponCode) {
		// Fetch the contest
		Contest contest = contestRepository.findById(contestId)
				.orElseThrow(() -> new RuntimeException("Contest not found"));
		// Check if userId is null
		if (userId == null) {
			return "UserId not found";
		}
		// Check if the contest is full
		if ("FULL".equals(contest.getStatus())) {
			return "Contest is full";
		}
		// Fetch the user
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		// Check if the user has already joined the contest
		boolean userAlreadyJoined = joinContestRepository.existsByContestAndUser(contest, user);
		if (userAlreadyJoined) {
			return "User has already joined this contest";
		}
		// Check the number of participants
		// int participantCount = joinContestRepository.countByContest(contest);
		// if (participantCount >= contest.getSeats()) {
		// contest.setStatus("FULL");
		// contestRepository.save(contest);
		// return "Contest is full";
		// }
		// Create and save the JoinContest record
		JoinContest joinContest = new JoinContest();
		joinContest.setContest_id(contestId);
		joinContest.setUser_id(userId);
		joinContest.setCouponCode(couponCode);
		joinContestRepository.save(joinContest);
		// Update the status if the contest is now full
		// participantCount = joinContestRepository.countByContest(contest);
		// if (participantCount >= contest.getSeats()) {
		// contest.setStatus("FULL");
		// contestRepository.save(contest);
		// }
		return "Joined successfully";
	}

	private static final Logger logger = LoggerFactory.getLogger(ContestService.class);

	@Scheduled(fixedRate = 6000)
	public List<Winner> checkContests() {
		List<Contest> endedContests = contestRepository
				.findByPostTimeBeforeAndStatus(new Timestamp(System.currentTimeMillis()), "Closed");
		List<Winner> winners = new ArrayList<>();
		for (Contest contest : endedContests) {
			// Check if the contest already has a winner
			if (!winnerRepository.existsByContest(contest)) {
				Winner winner = pickWinner(contest);
				if (winner != null) {
					winners.add(winner);
				}
			}
			contest.setStatus("completed");
			contestRepository.save(contest);
		}
		return winners;
	}

	@Transactional
	public Winner pickWinner(Contest contest) {
		List<JoinContest> participants = joinContestRepository.findByContest(contest);
		if (!participants.isEmpty()) {
			Random random = new Random();
			JoinContest joinContestWinner = participants.get(random.nextInt(participants.size()));
			Winner newWinner = new Winner(joinContestWinner.getUser(), contest, LocalDateTime.now());
			logger.info("Saving winner: User ID = {}, Contest ID = {}", newWinner.getUser().getUserId(),
					contest.getContestId());
			winnerRepository.save(newWinner);
			return newWinner;
		} else {
			logger.warn("No participants found for contest ID {}", contest.getContestId());
			return null;
		}
	}

	public List<Winner> getTodayWinners() {
		LocalDate today = LocalDate.now();
		LocalDateTime startOfDay = today.atStartOfDay();
		LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
		return winnerRepository.findByWinningTimeBetween(startOfDay, endOfDay);
	}

	public String delete(Long id) {
		contestRepository.deleteById(id);
		return "Deleted successfully:" + id;
	}

}
