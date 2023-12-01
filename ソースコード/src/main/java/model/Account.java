package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アカウントモデルクラス
 */
public class Account implements Serializable {
	private int id; 
	private String email;
	private String password;
	private String name;
	private int isDeleted;
	private Timestamp createdAt;
	private Timestamp updateAt;

	public Account() {}
	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public int getId() { return id; }
	public void setId(int id) {this.id = id;}

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getIsDeleted() { return isDeleted; }
	public void setIsDeleted(int isDeleted) {this.isDeleted = isDeleted; }

	public Timestamp getCreatedAt() { return createdAt; }
	public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

	public Timestamp getUpdateAt() { return updateAt; }
	public void setUpdateAt(Timestamp updateAt) { this.updateAt = updateAt; }

}
