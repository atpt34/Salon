<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h1><fmt:message key="hello"/> <fmt:message key="admin"/> ${sessionScope.user}</h1>
    <p> <a href="${pageContext.request.contextPath}/records" ><fmt:message key="page.records"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/comments" ><fmt:message key="page.comments"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/masters" ><fmt:message key="page.masters"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/createMaster" ><fmt:message key="page.createMaster"/></a> </p>
    
    <jsp:include page="../footer.jsp" />
    
</body>
</html>