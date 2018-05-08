<%@ include file="tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="error_tag.jsp"></jsp:include>
    
    <h1><fmt:message key="page.register"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/register">
        <fmt:message key="name"/><input type="text" name="name"><br/>
        <fmt:message key="pass"/><input type="password" name="pass"><br/>
        <fmt:message key="email"/><input type="text" name="email"><br/>
        <fmt:message key="fullname"/><input type="text" name="fullname"><br/>
       <input class="button" type="submit" value="<fmt:message key="page.register"/>">
    </form>
    
    <jsp:include page="footer.jsp" />
</body>
</html>