<%@ include file="tags.jsp" %>
 <h1><fmt:message key="page.schedules"/></h1>
  <c:forEach var="sc" items="${schedules}">
  <fmt:message key="master"/>: ${sc.key.fullname}  <br/>
  <fmt:message key="schedule"/>: <br/>
  			<c:forEach var="scd" items="${sc.value}">
  			 ${scd.day } ${scd.startHour } - ${scd.endHour }<br/> 
  		   </c:forEach>
  		<br/>
  		<br/>
</c:forEach>

<h1><fmt:message key="page.pages"/></h1>	
  <c:forEach var="i" begin="1" end="${totalPages}">
  	<a href="<c:url value="/index?page=${i}"/>" > ${i} </a>
  </c:forEach>