<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/style.css">

</head>
<body>


	<jsp:include page="_menu.jsp" />

	<div class="page-title">Product</div>

	<form:form action="saveProduct" modelAttribute="product" method="POST">
		<table style="text-align: left;">	
			<tr>
				<td>Code *</td>
				<td style="color:red;">
				<c:if test="${not empty product.code}">
				<form:hidden path="code"/>
                      ${product.code}
                 </c:if>                
               <c:if test="${empty product.code}">
				<form:input path="code"/>
				</c:if>
				</td>
			</tr>

			<tr>
				<td>Name *</td>
				<td><form:input path="name" /></td>
			</tr>

			<tr>
				<td>Price *</td>
				<td><form:input path="price" /></td>
			</tr>
			
			
			<tr>
				<td>Create_Date *</td>
				<td><form:input path="createDate"/></td>
			</tr>
			
			<tr>
			
				 <td><input type="submit" value="Save" class="save"></td>
			</tr>
		</table>
	</form:form>






</body>
</html>