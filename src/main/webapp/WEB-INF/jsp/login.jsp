<%@ include file="tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="error_tag.jsp"></jsp:include>

    
    <h1><fmt:message key="page.login"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <fmt:message key="name"/><input type="text" name="name"><br/>
        <fmt:message key="pass"/><input type="password" name="pass"><br/><br/>
       <input class="button" type="submit" value="<fmt:message key="page.login"/>">
    </form>
    
    <jsp:include page="footer.jsp" />
</body>
</html>