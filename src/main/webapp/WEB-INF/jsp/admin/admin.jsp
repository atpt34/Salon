<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <jsp:include page="../error_tag.jsp" />
    
    <h1><fmt:message key="hello"/> <fmt:message key="admin"/> <c:out value="${sessionScope.user.get().fullname}"/> </h1>
    
    <p> <a href="${pageContext.request.contextPath}/admin/comments" ><fmt:message key="page.comments"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/admin/users" ><fmt:message key="page.users"/></a> </p>
    
    <jsp:include page="../footer.jsp" />
    
</body>
</html>