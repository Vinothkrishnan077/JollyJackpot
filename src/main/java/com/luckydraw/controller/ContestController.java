package com.luckydraw.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckydraw.model.Contest;
import com.luckydraw.model.ContestDTO;
import com.luckydraw.model.Winner;
import com.luckydraw.service.ContestService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/contest")
public class ContestController {
	@Autowired
	ContestService contestService;

	@GetMapping("/list")
	public List<ContestDTO> list() {
		return contestService.list();
	}

	@GetMapping("/{id}")
	public Contest get(@PathVariable Long id) {
		return contestService.get(id);
	}

	@GetMapping("/search")
	public List<Contest> search(Contest contest) {
		return contestService.searchall(contest);
	}

	@PostMapping
	public Contest save(@RequestBody Contest contest) {
		return contestService.saveAll(contest);
	}

	@PutMapping
	public Contest update(@RequestBody Contest contest) {
		return contestService.updateall(contest);
	}

	@PostMapping("/{contestId}/join")
	public String joinContest(@PathVariable Long contestId, @RequestParam Long userId,
			@RequestParam String couponCode) {
		System.out.printf("userId" + userId, "contestId" + contestId);
		return contestService.joinContest(contestId, userId, couponCode);
	}

	private static final Logger logger = LoggerFactory.getLogger(ContestService.class);

	@GetMapping("/check-contests-winner")
	public List<Winner> checkContests() {
		List<Winner> winners = contestService.checkContests();
		if (winners.isEmpty()) {
			logger.info("No contests ended or no participants found for contests.");
		}
		return winners;
	}

	@GetMapping("/today-winners")
	public List<Winner> getTodayWinners() {
		return contestService.getTodayWinners();
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		return contestService.delete(id);
	}

}
