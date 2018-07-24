package com.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="Order_Details")
public class OrderDetails {
	
	
	@Id
	@Column(name="ID" , nullable=false )
	private String id;
	
	
	@Column(name="Amount" , nullable=false )
	private double amount;
	
	@Column(name="Price" , nullable=false )
	private double price;
	
	@Column(name="Quanity" , nullable=false )
	private int qt;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_ID", nullable=false,
	foreignKey = @ForeignKey(name="ORDER_DETAIL_ORD_FK"))
	private Orders orderId;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID", nullable=false,
	foreignKey = @ForeignKey(name="ORDER_DETAIL_PROD_FK"))
	private Product productId;
	
	public OrderDetails() {
		
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQt() {
		return qt;
	}

	public void setQt(int qt) {
		this.qt = qt;
	}

	public Orders getOrderId() {
		return orderId;
	}

	public void setOrderId(Orders orderId) {
		this.orderId = orderId;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "OrderDetails [id=" + id + ", amount=" + amount + ", price=" + price + ", qt=" + qt + ", orderId="
				+ orderId + ", productId=" + productId + "]";
	}

	

}
