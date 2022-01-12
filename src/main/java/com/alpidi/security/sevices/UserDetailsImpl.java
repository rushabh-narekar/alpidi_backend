package com.alpidi.security.sevices;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alpidi.model.User;
import com.alpidi.security.sevices.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;

	private String username;
	private String firstname;
	private String lastname;

	private String email;
	
	private String role;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String id, String role, String email,String firstname,String lastname, String password) {
		this.id = id;
		this.role = role;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	public static UserDetailsImpl build(User user) {
		//List<GrantedAuthority> authorities = user.getRoles().stream()
				//.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				//.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getRole(), 
				user.getEmail(),
				user.getFirstname(),
				user.getLastname(),
				user.getPassword());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}

