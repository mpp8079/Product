<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>


	<div class="menu-container">

	<a href="${pageContext.request.contextPath}/">Home</a> 
	| 
	<a href="${pageContext.request.contextPath}/productList"> Product List </a> 
	| 
	<a href="${pageContext.request.contextPath}/addProduct"> Create Product </a>
	  |
   <a href="${pageContext.request.contextPath}/shoppingCart">
      My Cart
   </a>
   |
     <security:authorize  access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
     <a href="${pageContext.request.contextPath}/orderList">
         Order List
     </a>
     |
   </security:authorize>
	
	</div>