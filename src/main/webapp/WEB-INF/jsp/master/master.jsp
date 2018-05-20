<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <jsp:include page="../error_tag.jsp" />
    
    <h1><fmt:message key="hello"/><fmt:message key="master"/> ${sessionScope.user.get().fullname}</h1>
    
    <p> <a href="${pageContext.request.contextPath}/master/schedules" ><fmt:message key="page.schedules"/></a> </p>
    <p> <a href="${pageContext.request.contextPath}/master/create_schedule_page" ><fmt:message key="page.createSchedule"/></a> </p>
    
    <jsp:include page="../footer.jsp" />
    
</body>
</html>