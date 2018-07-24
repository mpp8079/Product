<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<div class="header-container">
 
    <div class="site-name">Online Shop</div>
 
    <div class="header-bar">
        <c:if test="${pageContext.request.userPrincipal.name != null}">
        Hello
           <a href="${pageContext.request.contextPath}/accountInfo">
                ${pageContext.request.userPrincipal.name} </a>
         &nbsp;|&nbsp;
           <a href="${pageContext.request.contextPath}/logout">Logout</a>
 
        </c:if>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </c:if>
    </div>
</div>