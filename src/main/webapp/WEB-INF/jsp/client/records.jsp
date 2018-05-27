<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h2><fmt:message key="page.records"/></h2>
    <c:forEach var="rc" items="${records}">
		<c:out value="${rc.day}"/>  <c:out value="${rc.hour}"/> 
		<a href="${pageContext.request.contextPath}/client/delete_record?id=${rc.id}" ><fmt:message key="page.delete"/></a>
		<br/>
		<c:forEach var="sc" items="${rc.schedules }">
		  <c:out value="${sc.master.fullname }"/>  <br/> 
		</c:forEach>
		
		
		<c:if test="${rc.comment == null}">
			<form method="post" action="${pageContext.request.contextPath}/client/create_comment">
		        <fmt:message key="text"/> <input type="text" name="text" /><br/>
		        <fmt:message key="stars"/> <input type="number" name="stars" min="1" max="5"/><br/>
		        <input type="hidden" name="id" value='<c:out value="${rc.id }"/>'/>
		        <input class="button" type="submit" value="<fmt:message key="page.update"/>">
	    	</form>
		</c:if> 
		<c:if test="${rc.comment != null}">
			<c:out value="${rc.comment.text }"/> 
			<c:out value="${rc.comment.stars }"/> <br/>
		</c:if> 
		<br/>

	</c:forEach>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>