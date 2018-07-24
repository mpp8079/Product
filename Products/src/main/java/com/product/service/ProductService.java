package com.product.service;

import java.util.List;

import com.product.entity.Product;

public interface ProductService {
	
public List<Product> getProducts();
	
	public void deleteProduct(String code);
	
	public void addOrUpdateProduct(Product product);
	
	public Product getProduct(String code);
	
	

}
