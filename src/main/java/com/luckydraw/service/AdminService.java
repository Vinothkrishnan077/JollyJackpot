package com.luckydraw.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.luckydraw.model.Admin;
import com.luckydraw.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	AdminRepository adminRepository;

	public List<Admin> getAllList() {
		return adminRepository.findAll();
	}

	public Admin getById(Long adminId) {
		System.out.println(">>>>>>" + adminId);
		return adminRepository.findById(adminId).get();
	}

	public Admin validateadminCredentials(Admin admin) {
		Admin dbadmin = adminRepository.findByEmail(admin.getEmail());
		System.out.println(">>>>>>>>" + dbadmin);
		if (dbadmin == null) {
			return null; // admin not found
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean passwordMatches = passwordEncoder.matches(admin.getPassword(), dbadmin.getPassword());
		if (passwordMatches) {
			dbadmin.setPassword(null);
			return dbadmin; // Password match
		}
		return null;
	}

	public Admin getadminById(Long adminId) {
		return adminRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("admin not found with id: " + adminId));
	}

	public Admin saveAll(Admin admin) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(admin.getPassword());
		admin.setPassword(encodedPassword);
		// Check if a admin with the same email already exists
		Admin existingadmin = adminRepository.findByEmail(admin.getEmail());
		if (existingadmin != null) {
			// admin with the same email already exists
			throw new RuntimeException("admin already Registed with this email");
		}
		// If no existing admin is found, save the new admin
		return adminRepository.save(admin);
	}

	public byte[] getProfilePicture(Long adminId) {
		Admin admin = adminRepository.findById(adminId).orElse(null);
		if (admin != null) {
			return admin.getProfilePicture();
		} else {
			return null;
		}
	}

	public Admin uploadProfilePicture(Long adminId, MultipartFile file) throws IOException {
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new IllegalArgumentException("admin not found with ID: " + adminId));
		admin.setProfilePicture(file.getBytes());
		Admin updateadmin = adminRepository.save(admin);
		return updateadmin;
	}

	public Admin updateAll(Admin admin) {
		return adminRepository.saveAndFlush(admin);
	}

	public String deleteAll(Long adminId) {
		adminRepository.deleteById(adminId);
		return "admin Deleted Sucessfully" + adminId;
	}

	public List<Admin> searchall(Admin admin) {
		return adminRepository.findAll(Example.of(admin));
	}

}
