package com.shekhar.shopeasy.serviceimpl;

import com.shekhar.shopeasy.dto.request.RegisterRequest;
import com.shekhar.shopeasy.dto.response.ApiResponse;
import com.shekhar.shopeasy.entity.Role;
import com.shekhar.shopeasy.entity.User;
import com.shekhar.shopeasy.enums.AuthProvider;
import com.shekhar.shopeasy.enums.RoleType;
import com.shekhar.shopeasy.exception.*;
import com.shekhar.shopeasy.repository.RoleRepository;
import com.shekhar.shopeasy.repository.UserRepository;
import com.shekhar.shopeasy.service.AuthService;
import com.shekhar.shopeasy.util.FileUploadUtil;
import com.shekhar.shopeasy.validation.PasswordValidator;
import com.shekhar.shopeasy.validation.PhoneValidator;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse register(RegisterRequest request) {

        // =========================
        // FULLNAME VALIDATION
        // =========================

        if (request.getFullname() == null ||
                request.getFullname().trim().length() < 3) {

            throw new UserRegistrationException(
                    "Full name must contain at least 3 characters"
            );
        }

        // =========================
        // EMAIL VALIDATION
        // =========================

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "Email already registered"
            );
        }

        // =========================
        // PHONE VALIDATION
        // =========================

        if (!PhoneValidator.isValid(
                request.getPhoneNumber())) {

            throw new InvalidPhoneException(
                    "Invalid Indian mobile number"
            );
        }

        if (userRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new PhoneAlreadyExistsException(
                    "Phone number already registered"
            );
        }

        // =========================
        // PASSWORD MATCH
        // =========================

        if (!request.getPassword()
                .equals(request.getConfirmPassword())) {

            throw new PasswordMismatchException(
                    "Passwords do not match"
            );
        }

        // =========================
        // PASSWORD STRENGTH
        // =========================

        if (!PasswordValidator.isValid(
                request.getPassword())) {

            throw new WeakPasswordException(
                    "Password must contain uppercase, lowercase, number and special character"
            );
        }

        // =========================
        // ROLE FETCH
        // =========================

        Role userRole = roleRepository
                .findByName(RoleType.ROLE_USER)
                .orElseThrow(() ->
                        new RuntimeException("ROLE_USER not found"));

        // =========================
        // IMAGE SAVE
        // =========================

        String imageName = null;

        try {

            imageName = FileUploadUtil
                    .saveProfileImage(
                            request.getProfileImage()
                    );

        } catch (Exception e) {

            throw new InvalidImageException(
                    "Profile image upload failed"
            );
        }

        // =========================
        // USER BUILD
        // =========================

        User user = new User();

        user.setFullname(
                request.getFullname().trim()
        );

        user.setEmail(
                request.getEmail().trim().toLowerCase()
        );

        user.setPhoneNumber(
                request.getPhoneNumber()
        );

        user.setGender(
                request.getGender()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        // NEVER SAVE CONFIRM PASSWORD
        user.setConfirmPassword(null);

        user.setEnabled(true);

        user.setAccountNonLocked(true);

        user.setFailedAttempts(0);

        user.setProfileImage(imageName);

        user.setProvider(AuthProvider.LOCAL);

        user.setRoles(Set.of(userRole));

        // =========================
        // SAVE USER
        // =========================

        userRepository.save(user);

        // =========================
        // RESPONSE
        // =========================

        return new ApiResponse(
                true,
                "Registration successful",
                user.getEmail()
        );
    }
}