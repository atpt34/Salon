<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h1><fmt:message key="hello"/><fmt:message key="master"/> ${sessionScope.user}</h1>
    
    <p> <a href="${pageContext.request.contextPath}/master/schedules" ><fmt:message key="page.schedules"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/master/create_schedule_page" ><fmt:message key="page.createSchedule"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/master/records_page" ><fmt:message key="page.records"/></a> </p>
    
    <jsp:include page="../footer.jsp" />
    
</body>
</html>