<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <jsp:include page="../error_tag.jsp" />
    
    <h1><fmt:message key="page.schedules"/></h1>
    <form method="POST">
	<input type="submit" formaction="${pageContext.request.contextPath}/index" style="visibility: hidden; display: none;">
	<fmt:message key="recordDate"/>
	<input type="date" name="date" value="${date }"> |
	<fmt:message key="recordTime"/>
    <input type="time" name="time" step="3600" value="${time }"/>
     <br/><br/>
	<c:forEach var="sc" items="${schedules}">
		<input type="checkbox" name="id" value="${sc.id}" />${sc.id} ${sc}<br/>
	</c:forEach>
   	  <br/><br/>
	<button formaction="${pageContext.request.contextPath}/client/create_record"><fmt:message key="page.create"/></button>
	<button formaction="${pageContext.request.contextPath}/client/search_schedule"><fmt:message key="page.search"/></button>
	</form>

    <jsp:include page="../footer.jsp" />
</body>
</html>