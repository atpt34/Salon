<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h2 align="center"><fmt:message key="page.schedule"/></h2>
    
	<h2>create comment form for <c:out value="${id }" /></h2>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>