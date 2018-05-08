<%@ include file="tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <h2 align="center"><fmt:message key="page.title"/></h2>
    <p> <a href="${pageContext.request.contextPath}/schedules" ><fmt:message key="page.schedules"/></a> </p>
    
    <jsp:include page="footer.jsp" />
</body>
</html>