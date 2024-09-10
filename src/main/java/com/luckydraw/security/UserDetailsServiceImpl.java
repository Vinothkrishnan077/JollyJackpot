package com.luckydraw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.luckydraw.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.luckydraw.model.User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found for email " + username);
		}

		return User.builder().username(user.getEmail()).password(user.getPassword()) // Assuming the password is already
				// encoded in the database
				// .roles("ADMIN") // Set the roles as needed
				.build();
	}
}
