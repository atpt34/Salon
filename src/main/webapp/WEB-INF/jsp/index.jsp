<%@ include file="tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <h2 align="center"><fmt:message key="page.title"/></h2>
    
    <jsp:include page="paged_schedules_form.jsp" /> 
    
    <jsp:include page="footer.jsp" />
</body>
</html>