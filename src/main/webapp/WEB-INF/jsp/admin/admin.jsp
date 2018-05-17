<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h1><fmt:message key="hello"/> <fmt:message key="admin"/> ${sessionScope.user.get().fullname}</h1>
    <p> <a href="${pageContext.request.contextPath}/admin/records" ><fmt:message key="page.records"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/admin/comments_page" ><fmt:message key="page.comments"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/admin/users" ><fmt:message key="page.users"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/admin/makeMaster_page" ><fmt:message key="page.makeMaster"/></a> </p>
    
    <jsp:include page="../footer.jsp" />
    
</body>
</html>