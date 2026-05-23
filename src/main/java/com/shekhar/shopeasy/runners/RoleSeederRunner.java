package com.shekhar.shopeasy.runners;

import com.shekhar.shopeasy.entity.Role;
import com.shekhar.shopeasy.enums.RoleType;
import com.shekhar.shopeasy.repository.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeederRunner implements CommandLineRunner {

	private final RoleRepository roleRepository;

	public RoleSeederRunner(RoleRepository roleRepository) {

		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) {

		// ROLE_USER
		if (roleRepository.findByName(RoleType.ROLE_USER).isEmpty()) {

			roleRepository.save(new Role(RoleType.ROLE_USER));
		}

		// ROLE_ADMIN
		if (roleRepository.findByName(RoleType.ROLE_ADMIN).isEmpty()) {

			roleRepository.save(new Role(RoleType.ROLE_ADMIN));
		}

		System.out.println("✅ Roles seeded successfully");
	}
}