package com.luckydraw.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.luckydraw.model.Admin;
import com.luckydraw.model.CustomException;
import com.luckydraw.model.User;
import com.luckydraw.model.Winner;
import com.luckydraw.repository.AdminRepository;
import com.luckydraw.repository.UserRepository;
import com.luckydraw.repository.WinnerRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	WinnerRepository winnerRepository;

	public List<User> getAllList() {
		return userRepository.findAll();
	}

	public User getById(Long userId) {
		System.out.println(">>>>>>" + userId);
		return userRepository.findById(userId).get();
	}

	public List<User> searchall(User user) {
		return userRepository.findAll(Example.of(user));
	}

	public List<Winner> getWinnersByUserId(Long userId) {
		// Fetch the user by ID
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

		// Fetch the winners associated with the user
		return winnerRepository.findByUser(user);
	}

	public Object validateUserCredentials(User user) {
		User dbUser = userRepository.findByEmail(user.getEmail());
		// System.out.println(">>>>>>>>" + dbUser);
		if (dbUser != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			boolean passwordMatches = passwordEncoder.matches(user.getPassword(), dbUser.getPassword());
			if (passwordMatches) {
				dbUser.setPassword(null);
				return dbUser; // Password match

			}
		}

		Admin dbAdmin = adminRepository.findByEmail(user.getEmail());
		if (dbAdmin != null) {
			BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
			boolean passwordMacthes = passwordEncoder2.matches(user.getPassword(), dbAdmin.getPassword());
			if (passwordMacthes) {
				dbAdmin.setPassword(null);
				return dbAdmin;
			}
		}
		return null;
	}

	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
	}

	public User saveAll(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Admin admin = new Admin();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		Admin existingAdminPhoneNo = adminRepository.findByPhoneNumber(admin.getPhoneNumber());
		if (existingAdminPhoneNo != null) {
			throw new CustomException("Phone Number Already Exists");
		}

		Admin existingAdminEmail = adminRepository.findByEmail(admin.getEmail());
		if (existingAdminEmail != null) {
			throw new CustomException("Email Already Exists");
		}

		User existingUserPhoneNo = userRepository.findByPhoneNumber(user.getPhoneNumber());
		if (existingUserPhoneNo != null) {
			throw new CustomException("Phone Number Already Exists");
		}

		User existingUserEmail = userRepository.findByEmail(user.getEmail());
		if (existingUserEmail != null) {
			throw new CustomException("Email Already Exists");
		}

		return userRepository.save(user);
	}
//	private boolean isEmailVerifiedThroughGoogle(String email) {
//		return true;
//	}

	public byte[] getProfilePicture(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			return user.getProfilePicture();
		} else {
			return null;
		}
	}

	public User uploadProfilePicture(Long userId, MultipartFile file) throws IOException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
		user.setProfilePicture(file.getBytes());
		User updateUser = userRepository.save(user);
		return updateUser;
	}

	public User updateAll(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		Admin admin = new Admin();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		User existingUserPhoneNo = userRepository.findByPhoneNumber(user.getPhoneNumber());
		if (existingUserPhoneNo != null) {
			throw new CustomException("Phone Number Already Exists");
		}

		User existingUserEmail = userRepository.findByEmail(user.getEmail());
		if (existingUserEmail != null) {
			throw new CustomException("Email Already Exists");
		}

		return userRepository.saveAndFlush(user);
	}

	public String deleteAll(Long userId) {
		userRepository.deleteById(userId);
		return "User Deleted Sucessfully" + userId;
	}

	// ForgotPassword Methods--->
	public String generateOtp(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			String otp = generateRandomNumericString(6);
			user.setToken(otp);
			LocalDateTime expiryTime = LocalDateTime.now().plus(3, ChronoUnit.MINUTES);
			user.setTokenCreationDate(expiryTime);
			userRepository.save(user);
			sendOtpEmail(email, otp, expiryTime);
			return "OTP has send to your Email";
		} else {
			return "User with provided Email is not present...!";
		}
	}

	private void sendOtpEmail(String email, String token, LocalDateTime expiryTime) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(email);
			helper.setSubject("Your Otp for Forgot Password from LuckyWinner");
			String emailBody = "Your Otp is " + token + "...This will expire in 2 Minutes";
			helper.setText(emailBody);
			javaMailSender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private String generateRandomNumericString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public String resetPassWithOtp(String email, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userRepository.findByEmail(email);
		if (user.getToken() != null && user != null) {
			LocalDateTime tokenExpiry = user.getTokenCreationDate();
			if (tokenExpiry != null) {
				String encodedPasswordString = passwordEncoder.encode(newPassword);
				user.setPassword(encodedPasswordString);
				user.setToken(null);
				user.setTokenCreationDate(null);
				userRepository.save(user);
				return "Password Reset Completed Sucessfully (>_<)!";
			} else {
				return "Otp Has Expired";
			}
		} else {
			return "Invalid Otp";
		}
	}

	public boolean verifyOtp(String email, String token) {
		User user = userRepository.findByEmail(email);
		LocalDateTime tokenExpiry = user.getTokenCreationDate();
		if (user != null && user.getToken() != null && LocalDateTime.now().isBefore(tokenExpiry)) {
			String storeToken = user.getToken();
			user.setToken(null);
			user.setTokenCreationDate(null);
			System.out.println("Verified!");
			return storeToken.equals(token);
		}
		return false;
	}

}