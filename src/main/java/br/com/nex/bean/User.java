package br.com.nex.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "usuario")
public class User implements Serializable {

	@Id
	@Column(name = "username", length = 40)
	private String username;
	@Column(name = "password", length = 40)
	private String password;
	@Column(name = "enable", columnDefinition = "BOOLEAN")
	private boolean enable;
	@ManyToMany(targetEntity = Authority.class , fetch=FetchType.EAGER,  cascade = CascadeType.MERGE)
	@JoinTable(name = "auth_authority", joinColumns = @JoinColumn(name = "user_username"), inverseJoinColumns = @JoinColumn(name = "auth_authority"))
	@IndexColumn(name = "fk" , nullable = false, base = 1)	
	private List<Authority> authorities;

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

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
}