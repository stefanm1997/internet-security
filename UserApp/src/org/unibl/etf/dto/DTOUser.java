package org.unibl.etf.dto;

import java.io.Serializable;

public class DTOUser implements Serializable {

	private String username, password, role;
	private int idKorisnika;

	public DTOUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DTOUser(int idKorisnika, String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.idKorisnika = idKorisnika;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getIdKorisnika() {
		return idKorisnika;
	}

	public void setIdKorisnika(int idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

	@Override
	public String toString() {
		return "DTOUser [username=" + username + ", password=" + password + ", role=" + role + ", idKorisnika="
				+ idKorisnika + "]";
	}
}
