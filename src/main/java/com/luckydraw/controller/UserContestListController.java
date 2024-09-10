package com.luckydraw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckydraw.model.UserContestList;
import com.luckydraw.service.UserContestListService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/usercontestlist")
public class UserContestListController {
	@Autowired
	UserContestListService userContestListService;

	@GetMapping("/list")
	public List<UserContestList> list() {
		return userContestListService.list();
	}

	@GetMapping("/{id}")
	public UserContestList get(@PathVariable Long id) {
		return userContestListService.get(id);
	}

	@GetMapping("/search")
	public List<UserContestList> search(UserContestList userContestList) {
		return userContestListService.searchall(userContestList);
	}

	@PostMapping
	public UserContestList save(@RequestBody UserContestList userContestList) {
		return userContestListService.saveall(userContestList);
	}

	@PutMapping
	public UserContestList update(@RequestBody UserContestList userContestList) {
		return userContestListService.updateall(userContestList);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		return userContestListService.delete(id);
	}

}
