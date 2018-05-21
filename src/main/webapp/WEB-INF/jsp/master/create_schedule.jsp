<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h2 align="center"><fmt:message key="page.schedule"/></h2>
    
	<form method="post" action="${pageContext.request.contextPath}/master/create_schedule">
        <fmt:message key="date"/> <input type="date" name="date" value=""> <br/>
        <fmt:message key="start_time"/> <input type="time" name="start_time" step="3600" value=""/> <br/>
        <fmt:message key="finish_time"/> <input type="time" name="finish_time" step="3600" value=""/> <br/>
        <input class="button" type="submit" value="<fmt:message key="page.create"/>">
   	</form>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>