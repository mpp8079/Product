package com.product.dao;

import java.util.List;

import com.product.entity.Product;

public interface ProductDAO {

	public List<Product> getProducts();
	
	public void deleteProduct(String code);
	
	public void addOrUpdateProduct(Product product);
	
	public Product getProduct(String code);

	
	    
	    
	  
}
