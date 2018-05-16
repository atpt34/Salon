<%@ include file="tags.jsp" %>
<h1><fmt:message key="page.schedules"/></h1>
<c:forEach var="sc" items="${schedules}">
	${sc }<br/>
</c:forEach>