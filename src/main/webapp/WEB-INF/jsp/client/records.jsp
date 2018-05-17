<%@ include file="../tags.jsp" %>
<html>
<head>
    <title><fmt:message key="page.title"/></title>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <c:forEach var="rc" items="${records}">
		<input type="checkbox" name="id" value="${rc}" />${rc.day} ${rc.hour} ${empty rc.comment ? 'no comment on this record' : '' }<br/>
	</c:forEach>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>