<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    
    <c:forEach var="user" items="${users}">
    	<c:out value="${user.name }"/>
    	<c:out value="${user.fullname }"/>
    	<c:out value="${user.role }"/>
    	<c:url value="/admin/update_user" var="href">
    		<c:param name="id" value="${user.id }"/>
    	</c:url>
    	<a href="${href}"><fmt:message key="page.update"/></a>
    	<c:url value="/admin/delete_user" var="href2">
    		<c:param name="id" value="${user.id }"/>
    	</c:url>
    	<a href="${href2}"><fmt:message key="page.delete"/></a>
    	<br/>
    </c:forEach>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>