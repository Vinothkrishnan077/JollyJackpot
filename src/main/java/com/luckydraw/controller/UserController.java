package com.luckydraw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.luckydraw.model.User;
import com.luckydraw.model.Winner;
import com.luckydraw.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/list")
	private List<User> getAll() {
		return userService.getAllList();
	}

	@GetMapping("/{userId}")
	private User getbyId(@PathVariable Long userId) {
		return userService.getById(userId);
	}

	@GetMapping("/search")
	public List<User> search(User user) {
		return userService.searchall(user);
	}

	@GetMapping("/{userId}/winners")
	public List<Winner> getWinnersByUser(@PathVariable Long userId) {
		return userService.getWinnersByUserId(userId);
	}

	@GetMapping("/getProfile/{userId}")
	public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long userId) {
		byte[] profilePicture = userService.getProfilePicture(userId);
		if (profilePicture != null) {
			return ResponseEntity.ok().body(profilePicture);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/login")
	private Object validateUserCredentials(@RequestBody User user) throws Exception {
		Object u = userService.validateUserCredentials(user);
		if (u == null) {
			throw new Exception("User not found");
		}
		// System.out.printf("user",u);
		return u;
	}

	@PostMapping
	public User saveAll(@Valid @RequestBody User user) {
		return userService.saveAll(user);
	}

	@PutMapping("/uploadProfilePicture/{userId}")
	public ResponseEntity<?> uploadProfilePicture(@PathVariable Long userId,
			@RequestParam("profilePicture") MultipartFile file) {
		try {
			User updateUser = userService.uploadProfilePicture(userId, file);
			return ResponseEntity.status(HttpStatus.OK).body(updateUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to upload profile picture: " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public User updateAll(@RequestBody User user) {
		return userService.updateAll(user);
	}

	@DeleteMapping("/{id}")
	public String deleteAll(@PathVariable Long userId) {
		return userService.deleteAll(userId);
	}

	@PostMapping("/forgot-password")
	public String generateOtp(@RequestParam String email) {
		return userService.generateOtp(email);
	}

	@PostMapping("/reset-password")
	public String resetPassWithOtp(@RequestParam String email, @RequestParam String newPassword) {
		return userService.resetPassWithOtp(email, newPassword);
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<Map<String, Object>> verifyOtp(@RequestParam String email, @RequestParam String token) {
		boolean isOtpVerified = userService.verifyOtp(email, token);
		Map<String, Object> response = new HashMap<String, Object>();
		if (isOtpVerified) {
			response.put("Success", true);
		} else {
			response.put("Failed", false);
		}
		return ResponseEntity.ok(response);
	}

}
