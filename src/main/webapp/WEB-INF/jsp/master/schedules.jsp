<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h1><fmt:message key="page.schedules"/></h1>
	<c:forEach var="sc" items="${schedules}">
		<fmt:message key="page.schedule"/>: 
		<c:out value="${sc.day }"/> 
		<c:out value="${sc.startHour }"/> - <c:out value="${sc.endHour }"/> <br/>
		<fmt:message key="page.occupiedHours"/>: <br/> 
		<c:forEach var="sc" items="${sc.records }">
			<c:out value="${sc.hour }"/> <br/>
		</c:forEach> 
		<c:url value="/admin/delete_schedule" var="href2">
    		<c:param name="id" value="${sc.id }"/>
    	</c:url>
    	<a href="${href2}"><fmt:message key="page.delete"/></a>
		<br/>
	</c:forEach>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>
