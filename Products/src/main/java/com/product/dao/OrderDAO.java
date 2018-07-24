package com.product.dao;

import java.util.List;

import com.product.model.CartInfo;
import com.product.model.OrderDetailInfo;
import com.product.model.OrderInfo;
import com.product.model.PaginationResult;

public interface OrderDAO {
	
	 public void saveOrder(CartInfo cartInfo);
	 
	    public PaginationResult<OrderInfo> listOrderInfo(int page,
	            int maxResult, int maxNavigationPage);
	    
	    public OrderInfo getOrderInfo(String orderId);
	    
	    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);

}
