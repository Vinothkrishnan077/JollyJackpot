package com.luckydraw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luckydraw.model.UserContestList;

@Repository
public interface UserContestListRepository extends JpaRepository<UserContestList, Long> {

}
