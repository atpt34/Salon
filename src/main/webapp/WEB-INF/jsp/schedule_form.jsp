<%@ include file="tags.jsp" %>
<h1><fmt:message key="page.schedules"/></h1>
<c:forEach var="sc" items="${schedules}">
	<fmt:message key="page.schedule"/>: 
	<c:out value="${sc.day }"/> 
	<c:out value="${sc.startHour }"/> - <c:out value="${sc.endHour }"/> <br/>
	<fmt:message key="page.occupiedHours"/>: <br/> 
	<c:forEach var="sc" items="${sc.records }">
		<c:out value="${sc.hour }"/> <br/>
	</c:forEach> 
	<br/>
</c:forEach>