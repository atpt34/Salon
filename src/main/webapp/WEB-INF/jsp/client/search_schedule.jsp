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
	<fmt:message key="date"/>
	<input type="date" name="date" value="<c:out value="${date }"/>"> | 
	<fmt:message key="time"/>
    <input type="time" name="time" step="3600" value="<c:out value="${time }"/>"/> 
     <br/><br/>
     <c:if test="${empty schedules}">
     	<fmt:message key="search.no.results"/>
     </c:if>
	<c:forEach var="sc" items="${schedules}">
		<input type="checkbox" name="id" value="${sc.id}" />
		<c:out value="${sc.master.fullname }"/>
		<c:out value="${sc.master.email }"/>
		<c:out value="${sc.startHour }"/> - <c:out value="${sc.endHour }"/>
		<br/>
	</c:forEach>
   	  <br/><br/>
	<button formaction="${pageContext.request.contextPath}/client/create_record"><fmt:message key="page.create"/></button>
	<button formaction="${pageContext.request.contextPath}/client/search_schedule"><fmt:message key="page.search"/></button>
	</form>

    <jsp:include page="../footer.jsp" />
</body>
</html>