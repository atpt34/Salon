<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, java.io.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>  
<fmt:setBundle basename="messages"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="page.error"/></title>
</head>
    <body>
        <h2><fmt:message key="page.error"/></h2>
           <details>
            <summary> <c:out value="${exception.getMessage() }"/>
            <fmt:message key="<%= exception.getMessage() %>"/>
             </summary>
            <p> StackTrace:
            <%
			    StringWriter stringWriter = new StringWriter();
			    PrintWriter printWriter = new PrintWriter(stringWriter);
			    exception.printStackTrace(printWriter);
			    out.println(stringWriter);
			    printWriter.close();
			    stringWriter.close();
			%> 
            </p>
            </details>
        
    <br>
        <a href="${pageContext.request.contextPath}/index"><fmt:message key="page.index"/></a>

    </body>
</html>