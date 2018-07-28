<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.page-title  {
    font-size:120%;
    text-align: center;
    margin:10px 0px;
    color: red;
}
</style>

<title>Product List</title>
 

 
</head>
<body>


 

   <jsp:include page="_menu.jsp" />
  
   <fmt:setLocale value="en_US" scope="session"/>
   <br>
 
   <div class="page-title">Product List</div>

	<hr>
 
 
   <c:forEach items="${products}" var="prodInfo">
       <div class="product-preview-container">
           <ul>             
               <li>Code: ${prodInfo.code}</li>
               <li>Name: ${prodInfo.name}</li>
               <li>Price: <fmt:formatNumber value="${prodInfo.price}" type="currency"/></li>              
                        
                 <security:authorize  access="hasRole('ROLE_MANAGER')">       
                <li><a style="color:red;"
                     href="${pageContext.request.contextPath}/editProduct?code=${prodInfo.code}">
                       Edit Product
                     </a>
                </li>      
                 </security:authorize>
                 
                  <security:authorize  access="hasRole('ROLE_MANAGER')">  
                 <li><a style="color:green;"
                     href="${pageContext.request.contextPath}/deleteProduct?code=${prodInfo.code}"
                     onclick="if(!(confirm('Are you sure want to delete this customer?'))) return false"
                       >Delete Product
                     </a>
                </li>                 
                 </security:authorize>
                   <li><a
                   href="${pageContext.request.contextPath}/buyProduct?code=${prodInfo.code}">
                       Buy Now</a></li>
           </ul>
              
       </div>
       <hr> 
   </c:forEach>
 
</body>
</html>