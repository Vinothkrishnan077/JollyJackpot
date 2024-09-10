package com.luckydraw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckydraw.model.JoinContest;
import com.luckydraw.service.JoinContestService;

@RestController
@RequestMapping("/joincontest")
public class JoinContestController {
	@Autowired
	JoinContestService joinContestService;

	@GetMapping("/list")
	public List<JoinContest> list() {
		return joinContestService.list();
	}

	@GetMapping("/user/{userId}")
    public List<JoinContest> getByUserId(@PathVariable Long userId) {
        return joinContestService.findByUserId(userId);
    }
	
	@GetMapping("/{id}")
	public JoinContest get(@PathVariable Long id) {
		return joinContestService.get(id);
	}

	@GetMapping("/search")
	public List<JoinContest> search(JoinContest joinContest) {
		return joinContestService.searchall(joinContest);
	}

	@PostMapping
	public JoinContest save(@RequestBody JoinContest joinContest) {
		return joinContestService.saveall(joinContest);
	}

	@PutMapping
	public JoinContest update(@RequestBody JoinContest joinContest) {
		return joinContestService.updateall(joinContest);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		return joinContestService.delete(id);
	}

}
