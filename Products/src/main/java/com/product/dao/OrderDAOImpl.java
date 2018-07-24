package com.product.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.product.entity.OrderDetails;
import com.product.entity.Orders;
import com.product.entity.Product;
import com.product.model.CartInfo;
import com.product.model.CartLineInfo;
import com.product.model.CustomerInfo;
import com.product.model.OrderDetailInfo;
import com.product.model.OrderInfo;
import com.product.model.PaginationResult;

@Transactional
public class OrderDAOImpl implements OrderDAO {
	
	 @Autowired
	    private SessionFactory sessionFactory;
	 
	    @Autowired
	    private ProductDAO productDAO;
	 
	    private int getMaxOrderNum() {
	        String sql = "Select max(o.orderNum) from " + Orders.class.getName() + " o ";
	        Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery(sql);
	        Integer value = (Integer) query.uniqueResult();
	        if (value == null) {
	            return 0;
	        }
	        return value;
	    }
	 
	    @Override
	    public void saveOrder(CartInfo cartInfo) {
	        Session session = sessionFactory.getCurrentSession();
	 
	        int orderNum = this.getMaxOrderNum() + 1;
	        Orders order = new Orders();
	 
	        order.setId(UUID.randomUUID().toString());
	        order.setOrderNum(orderNum);
	        order.setOrderDate(new Date());
	        order.setAmount(cartInfo.getAmountTotal());
	 
	        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
	        order.setCusName(customerInfo.getName());
	        order.setCusEmail(customerInfo.getEmail());
	        order.setCusPhone(customerInfo.getPhone());
	        order.setCusAddress(customerInfo.getAddress());
	 
	        session.persist(order);
	 
	        List<CartLineInfo> lines = cartInfo.getCartLines();
	 
	        for (CartLineInfo line : lines) {
	            OrderDetails detail = new OrderDetails();
	            detail.setId(UUID.randomUUID().toString());
	            detail.setOrderId(order);
	            detail.setAmount(line.getAmount());
	            detail.setPrice(line.getProductInfo().getPrice());
	            detail.setQt(line.getQuantity());
	 
	            String code = line.getProductInfo().getCode();
	            Product product = this.productDAO.getProduct(code);
	            detail.setProductId(product);
	 
	            session.persist(detail);
	        }
	 
	        
	        cartInfo.setOrderNum(orderNum);
	    }
	 
	    // @page = 1, 2, ...
	    @Override
	    public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
	        String sql = "Select new " + OrderInfo.class.getName()//
	                + "(ord.id, ord.orderDate, ord.orderNum, ord.amount, "
	                + " ord.cusName, ord.cusAddress, ord.cusEmail, ord.cusPhone) " + " from "
	                + Orders.class.getName() + " ord "//
	                + " order by ord.orderNum desc";
	        Session session = this.sessionFactory.getCurrentSession();
	 
	        Query query = session.createQuery(sql);
	 
	        return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
	    }
	 
	    public Orders findOrder(String orderId) {
	        Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(Orders.class);
	        crit.add(Restrictions.eq("id", orderId));
	        return (Orders) crit.uniqueResult();
	    }
	 
	    @Override
	    public OrderInfo getOrderInfo(String orderId) {
	        Orders order = this.findOrder(orderId);
	        if (order == null) {
	            return null;
	        }
	        return new OrderInfo(order.getId(), order.getOrderDate(), //
	                order.getOrderNum(), order.getAmount(), order.getCusName(), //
	                order.getCusAddress(), order.getCusEmail(), order.getCusPhone());
	    }
	 
	    @Override
	    public List<OrderDetailInfo> listOrderDetailInfos(String orderId) {
	        String sql = "Select new " + OrderDetailInfo.class.getName() //
	                + "(d.id, d.productId.code, d.productId.name , d.qt,d.price,d.amount) "//
	                + " from " + OrderDetails.class.getName() + " d "//
	                + " where d.orderId.id = :orderId ";
	 
	        Session session = this.sessionFactory.getCurrentSession();
	 
	        Query query = session.createQuery(sql);
	        query.setParameter("orderId", orderId);
	 
	        return query.list();
	    }
	

}
