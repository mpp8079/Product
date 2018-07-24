package com.product.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.product.entity.Product;


@Repository
public class ProductDAOImpl implements ProductDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@Transactional
	public List<Product> getProducts() {
		Session session = sessionFactory.getCurrentSession();
		List<Product> product = session.createQuery("from Product order by code").list();
		return product;
	}

	@Override
	@Transactional
	public void deleteProduct(String code) {
		Session session = sessionFactory.getCurrentSession();
		Product product = session.get(Product.class, code);
		session.delete(product);
		
	}

	@Override
	@Transactional
	public void addOrUpdateProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		
	}

	@Override
	@Transactional
	public Product getProduct(String code) {
		Session session = sessionFactory.getCurrentSession();
		Product product = session.get(Product.class, code);
		return product;
	}

	
}
