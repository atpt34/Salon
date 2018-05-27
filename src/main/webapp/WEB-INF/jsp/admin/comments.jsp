<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h2><fmt:message key="page.comments"/></h2>
    <table border="1">
    	<thead>
   		<tr>
   			<th><fmt:message key="email"/></th>
   			<th><fmt:message key="stars"/></th>
   			<th><fmt:message key="text"/></th>
   		</tr>
    	</thead>
    	<tbody>
    	<c:forEach var="rc" items="${records}">
    	<tr>
	   		<td><c:out value="${rc.client.email}"/></td>
			<td><c:out value="${rc.comment.stars}"/></td>
	   		<td><c:out value="${rc.comment.text}"/></td>
   		</tr>
   		</c:forEach>
    	</tbody>
    </table>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>