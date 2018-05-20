<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <h2><fmt:message key="page.records"/></h2>
    <c:forEach var="rc" items="${records}">
		${rc.day} ${rc.hour}
		<a href="${pageContext.request.contextPath}/client/delete_record?id=${rc.id}" ><fmt:message key="page.delete"/></a>
		<c:if test="${empty rc.comment}">
		<a href="${pageContext.request.contextPath}/client/create_comment_page?id=${rc.id}" ><fmt:message key="page.createComment"/></a>
		</c:if> <br/>
		<c:forEach var="sc" items="${rc.schedules }">
		  ${sc.master.fullname } <br/> 
		</c:forEach>
		<br/>
		<br/>
	</c:forEach>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>