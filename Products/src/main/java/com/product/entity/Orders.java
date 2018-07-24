package com.product.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Orders",//
uniqueConstraints = { @UniqueConstraint(columnNames = "Order_Num") })
public class Orders {
	
	
	@Id
	@Column(name="ID", nullable=false )
	private String id;
	
	@Column(name="Amount", nullable=false )
	private double amount;
	
	@Column(name="Customer_Address", nullable=false )
	private String cusAddress;
	
	@Column(name="Customer_Email", nullable=false )
	private String cusEmail;
	
	@Column(name="Customer_Name", nullable=false )
	private String cusName;
	
	@Column(name="Customer_Phone", nullable=false )
	private String cusPhone;
	
	
	
	@Column(name="Order_Date", nullable=false )
	private Date orderDate;
	
	@Column(name="Order_Num", nullable=false )
	private int orderNum;
	
	
	public Orders() {
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getCusAddress() {
		return cusAddress;
	}


	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}


	public String getCusEmail() {
		return cusEmail;
	}


	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}


	public String getCusName() {
		return cusName;
	}


	public void setCusName(String cusName) {
		this.cusName = cusName;
	}


	public String getCusPhone() {
		return cusPhone;
	}


	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}


	public Date getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public int getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", amount=" + amount + ", cusAddress=" + cusAddress + ", cusEmail=" + cusEmail
				+ ", cusName=" + cusName + ", cusPhone=" + cusPhone + ", orderDate=" + orderDate + ", orderNum="
				+ orderNum + "]";
	}
	
	
	

}
