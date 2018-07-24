package com.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dao.ProductDAO;
import com.product.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO productDAO;

	@Override
	public List<Product> getProducts() {
		
		return productDAO.getProducts();
	}

	@Override
	public void deleteProduct(String code) {
		productDAO.deleteProduct(code);
		
	}

	@Override
	public void addOrUpdateProduct(Product product) {
		productDAO.addOrUpdateProduct(product);
		
	}

	@Override
	public Product getProduct(String code) {		
		return productDAO.getProduct(code);
	}

	
	

}
