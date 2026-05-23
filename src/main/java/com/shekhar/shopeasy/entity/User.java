package com.shekhar.shopeasy.entity;

import com.shekhar.shopeasy.enums.AuthProvider;
import com.shekhar.shopeasy.enums.GenderType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "phone_number") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String fullname;

	@Column(nullable = false)
	private String email;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GenderType gender;

	@Column(nullable = false)
	private String password;

	@Transient
	private String confirmPassword;

	private String profileImage;

	private boolean enabled;

	private boolean accountNonLocked;

	private int failedAttempts = 0;

	private LocalDateTime lockTime;

	@Enumerated(EnumType.STRING)
	private AuthProvider provider = AuthProvider.LOCAL;

	private String providerId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",

			joinColumns = @JoinColumn(name = "user_id"),

			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@PrePersist
	public void onCreate() {

		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {

		this.updatedAt = LocalDateTime.now();
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public LocalDateTime getLockTime() {
		return lockTime;
	}

	public void setLockTime(LocalDateTime lockTime) {
		this.lockTime = lockTime;
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setAccountNonLocked(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}
}