package beans;

import java.util.Date;

public class phone {
	private int id;
	private String provider;
	private String number;
	private int userId;
	private String isAdmin;
	
	

	public phone(int id, String provider, String number, int userId, String isAdmin) {
		super();
		this.id = id;
		this.provider = provider;
		this.number = number;
		this.userId = userId;
		this.isAdmin = isAdmin;
	}
	
	public phone(String provider, String number, int userId, String isAdmin) {
		this.provider = provider;
		this.number = number;
		this.userId = userId;
		this.isAdmin = isAdmin;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
