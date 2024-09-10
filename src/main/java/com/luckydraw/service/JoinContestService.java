package com.luckydraw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.luckydraw.model.JoinContest;
import com.luckydraw.model.User;
import com.luckydraw.repository.JoinContestRepository;
import com.luckydraw.repository.UserRepository;

@Service
public class JoinContestService {
	@Autowired
	JoinContestRepository joinContestRepository;
	
	   @Autowired
	    private UserRepository userRepository;
	
	public JoinContest get(Long id) {
		return joinContestRepository.findById(id).get();
	}

	public List<JoinContest> list() {
		return joinContestRepository.findAll();
	}
	
	 public List<JoinContest> findByUserId(Long userId) {
	        User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	        return joinContestRepository.findByUser(user);
	    }

	public List<JoinContest> searchall(JoinContest joinContest) {
		return joinContestRepository.findAll(Example.of(joinContest));
	}

	public JoinContest saveall(JoinContest joinContest) {
		return joinContestRepository.save(joinContest);
	}

	public JoinContest updateall(JoinContest joinContest) {
		return joinContestRepository.saveAndFlush(joinContest);
	}

	public String delete(Long id) {
		joinContestRepository.deleteById(id);
		return "Deleted successfully:" + id;
	}

}
