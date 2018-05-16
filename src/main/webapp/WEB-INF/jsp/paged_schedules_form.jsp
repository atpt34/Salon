<%@ include file="tags.jsp" %>
 <h1><fmt:message key="page.schedules"/></h1>
  <c:forEach var="sc" items="${schedules}">
  Master: ${sc.key}  <br/>
  Schedules: <c:forEach var="scd" items="${sc.value}"> ${scd} <br/> 
  		   </c:forEach>
  		<br/>
  		<br/>
</c:forEach>

<h1><fmt:message key="page.pages"/></h1>	
  <c:forEach var="i" begin="1" end="${totalPages}">
  	<a href="<c:url value="/index?page=${i}"/>" > ${i} </a>
  </c:forEach>