<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    <h2><fmt:message key="page.comments"/></h2>
    <c:forEach var="rc" items="${records}">
    	<c:out value="${rc.client.email}"></c:out>
    	<c:out value="${rc.comment.text}"></c:out>
    	<c:out value="${rc.comment.stars}"></c:out>
    	<br/>
    </c:forEach>
    <jsp:include page="../footer.jsp" />
</body>
</html>