<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <jsp:include page="../error_tag.jsp" />
    
    <h1><fmt:message key="hello"/> <fmt:message key="client"/> <c:out value="${sessionScope.user.get().fullname}"/> </h1>
    
    <p> <a href="${pageContext.request.contextPath}/client/records" ><fmt:message key="page.records"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/client/search_schedule_page" ><fmt:message key="page.searchSchedule"/></a> </p>
    
    <jsp:include page="../footer.jsp" />
    
</body>
</html>