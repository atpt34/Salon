<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h1><fmt:message key="hello"/> <fmt:message key="client"/> ${sessionScope.user}</h1>
    <p> <a href="${pageContext.request.contextPath}/records" ><fmt:message key="page.records"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/createComment" ><fmt:message key="page.createComment"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/schedules" ><fmt:message key="page.schedules"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/createRecord" ><fmt:message key="page.createRecord"/></a> </p>
    
    <jsp:include page="../footer.jsp" />
    
</body>
</html>