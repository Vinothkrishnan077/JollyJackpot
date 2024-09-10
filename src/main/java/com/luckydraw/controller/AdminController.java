package com.luckydraw.controller;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.luckydraw.model.Admin;
import com.luckydraw.service.AdminService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService adminService;

	@GetMapping("/list")
	private List<Admin> getAll() {
		return adminService.getAllList();
	}

	@GetMapping("/{adminId}")
	private Admin getbyId(@PathVariable Long adminId) {
		return adminService.getById(adminId);
	}

	@GetMapping("/{adminId}/profilePicture")
	public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long adminId) {
		// Fetch the Admin entity using the provided adminId
		Admin admin = adminService.getById(adminId);

		if (admin == null) {
			// If the admin does not exist, return a 404 Not Found response
			return ResponseEntity.notFound().build();
		}

		// Retrieve the profile picture data (byte array)
		byte[] profilePicture = admin.getProfilePicture();

		if (profilePicture == null || profilePicture.length == 0) {
			// If no profile picture data is set, return a 204 No Content response
			return ResponseEntity.noContent().build();
		}

		// Set the content type based on the file extension or default to a common image
		// type
		String fileName = admin.getProfilePictureName();
		MediaType mediaType = MediaType.IMAGE_JPEG; // Default to JPEG if type cannot be determined
		if (fileName != null) {
			if (fileName.endsWith(".png")) {
				mediaType = MediaType.IMAGE_PNG;
			} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
				mediaType = MediaType.IMAGE_JPEG;
			}
		}

		// Set the headers and content type
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		headers.setContentLength(profilePicture.length);

		// Return the profile picture data in the response
		return new ResponseEntity<>(profilePicture, headers, HttpStatus.OK);
	}

	@PostMapping("/login")
	private Admin validateAdminCredentials(@RequestBody Admin admin) throws Exception {
		Admin u = adminService.validateadminCredentials(admin);
		if (u == null) {
			throw new Exception("Admin not found");
		}
//		System.out.printf("Admin",u);
		return u;
	}

	@PostMapping
	public Admin saveAll(@Valid @RequestBody Admin admin) {
		return adminService.saveAll(admin);
	}

	@PutMapping("/uploadProfilePicture/{adminId}")
	public ResponseEntity<?> uploadProfilePicture(@PathVariable Long adminId,
			@RequestParam("profilePicture") MultipartFile file) {

		try {
			Admin admin = adminService.getById(adminId); // Assuming you have an adminId to find the Admin

			// Set the profile picture name and content
			admin.setProfilePictureName(file.getOriginalFilename());
			admin.setProfilePicture(file.getBytes());

			adminService.updateAll(admin); // Save the Admin entity with the updated profile picture

			return ResponseEntity.ok("Profile picture uploaded successfully");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile picture");
		}
	}

	@PutMapping
	public Admin updateAll(@RequestBody Admin admin) {
		return adminService.updateAll(admin);
	}

	@DeleteMapping("/{adminId}")
	public String deleteAll(@PathVariable Long adminId) {
		return adminService.deleteAll(adminId);
	}

	@GetMapping("/search")
	public List<Admin> search(Admin admin) {
		return adminService.searchall(admin);
	}
}
