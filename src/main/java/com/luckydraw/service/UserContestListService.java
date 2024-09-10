package com.luckydraw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.luckydraw.model.UserContestList;
import com.luckydraw.repository.UserContestListRepository;

@Service
public class UserContestListService {
	@Autowired
	UserContestListRepository userContestListRepository;

	public UserContestList get(Long id) {
		return userContestListRepository.findById(id).get();
	}

	public List<UserContestList> list() {
		return userContestListRepository.findAll();
	}

	public List<UserContestList> searchall(UserContestList userContestList) {
		return userContestListRepository.findAll(Example.of(userContestList));
	}

	public UserContestList saveall(UserContestList userContestList) {
		return userContestListRepository.save(userContestList);
	}

	public UserContestList updateall(UserContestList userContestList) {
		return userContestListRepository.saveAndFlush(userContestList);
	}

	public String delete(Long id) {
		userContestListRepository.deleteById(id);
		return "Deleted successfully:" + id;
	}

}
