package dev.lbell.models;

public class Comps {
	private int id, amount, userId, managerId;
	private String description, status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Comps() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comps(int amount, int userId, String description) {
		super();
		this.amount = amount;
		this.userId = userId;
		this.description = description;
	}
	public Comps(int id, int amount, int userId, String description, String status) {
		super();
		this.id = id;
		this.amount = amount;
		this.userId = userId;
		this.description = description;
		this.status = status;
	}
	public Comps(int amount, int userId, String description, String status) {
		super();
		this.amount = amount;
		this.userId = userId;
		this.description = description;
		this.status = status;
	}
	
	public Comps(int id, int amount, int userId, int managerId, String description, String status) {
		super();
		this.id = id;
		this.amount = amount;
		this.userId = userId;
		this.managerId = managerId;
		this.description = description;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Comps [id=" + id + ", amount=" + amount + ", userId=" + userId + ", managerId=" + managerId
				+ ", description=" + description + ", status=" + status + "]";
	}
	
	
	
}
