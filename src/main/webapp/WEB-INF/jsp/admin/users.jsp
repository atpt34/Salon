<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <table border="1">
    	<thead>
   		<tr>
   			<th><fmt:message key="name"/></th>
   			<th><fmt:message key="fullname"/></th>
   			<th><fmt:message key="role"/></th>
   			<th><fmt:message key="page.update"/></th>
   			<th><fmt:message key="page.delete"/></th>
   		</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="user" items="${users}">
    	<tr>
	    	<td><c:out value="${user.name }"/></td>
	    	<td><c:out value="${user.fullname }"/></td>
	    	<td><c:out value="${user.role }"/></td>
	    	<c:if test="${user.name != sessionScope.user.get().name }">
	    	<td><c:url value="/admin/update_user" var="href">
	    		<c:param name="id" value="${user.id }"/>
	    	</c:url>
	    	<a href="${href}"><fmt:message key="page.update"/></a></td>
	    	<td><c:url value="/admin/delete_user" var="href2">
	    		<c:param name="id" value="${user.id }"/>
	    	</c:url>
	    	<a href="${href2}"><fmt:message key="page.delete"/></a></td>
	    	</c:if>
    	</tr>
   		</c:forEach>
    	</tbody>
    </table>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>